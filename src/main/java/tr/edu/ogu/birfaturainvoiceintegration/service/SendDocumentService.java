package tr.edu.ogu.birfaturainvoiceintegration.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import tr.edu.ogu.birfaturainvoiceintegration.dto.SendDocumentApiRequest;
import tr.edu.ogu.birfaturainvoiceintegration.dto.SendDocumentUserRequest;
import tr.edu.ogu.birfaturainvoiceintegration.dto.response.ApiResponse;
import tr.edu.ogu.birfaturainvoiceintegration.dto.response.SendDocumentResult;
import tr.edu.ogu.birfaturainvoiceintegration.util.FileUtil;
import tr.edu.ogu.birfaturainvoiceintegration.util.XmlUtil;

import java.io.File;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SendDocumentService {

    private final RestClient birFaturaRestClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ApiResponse<SendDocumentResult> sendDocument(SendDocumentApiRequest request) {
        try {
            String rawResponse = birFaturaRestClient.post()
                    .uri("/SendDocument")
                    .body(request)
                    .retrieve()
                    .body(String.class);

            return objectMapper.readValue(rawResponse, new TypeReference<>() {});
        } catch (HttpClientErrorException e) {
            String errorBody = e.getResponseBodyAsString();
            try {
                return objectMapper.readValue(errorBody, new TypeReference<>() {});
            } catch (JsonProcessingException ex) {
                throw new RuntimeException("BirFatura hata cevabı ayrıştırılamadı: " + errorBody, ex);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("BirFatura başarılı cevabı ayrıştırılamadı", e);
        }
    }

    public ApiResponse<SendDocumentResult> sendDocument(SendDocumentUserRequest request) {

        SendDocumentApiRequest apiRequest = null;

        try {
            File xmlFile = XmlUtil.createInvoiceXml(
                    UUID.randomUUID(),
                    LocalDate.now(),
                    OffsetTime.now(ZoneOffset.ofHours(3)),

                    request.getCurrencyCode(),
                    request.getInvoiceTypeCode(),
                    request.getNoteText(),
                    request.getSupplierVkn(),
                    request.getSupplierName(),
                    request.getSupplierStreet(),
                    request.getSupplierBuildingNumber(),
                    request.getSupplierSubdivisionName(),
                    request.getSupplierCity(),
                    request.getSupplierPostalCode(),
                    request.getSupplierEmail(),
                    request.getCustomerTckn(),
                    request.getCustomerFirstName(),
                    request.getCustomerLastName(),
                    request.getCustomerStreet(),
                    request.getCustomerBuildingNumber(),
                    request.getSupplierSubdivisionName(),
                    request.getCustomerCity(),
                    request.getCustomerPostalCode(),
                    request.getItemName(),
                    request.getQuantity(),
                    request.getUnitPrice(),
                    request.getTaxPercent(),
                    "test_invoice.xml");

            String base64EncodedZip = FileUtil.zipAndEncode(xmlFile);

            apiRequest = new SendDocumentApiRequest();
            apiRequest.setReceiverTag("urn:mail:test@firma.com");
            apiRequest.setDocumentBytes(base64EncodedZip);
            apiRequest.setDocumentNoAuto(true);
            apiRequest.setSystemTypeCodes("EARSIV");



        } catch (Exception e) {
            System.err.println("Error message: " + e.getMessage());
            e.printStackTrace();
        }


        return sendDocument(apiRequest);
    }
}

/* Notes
------ 1 ------
retrieve() default olarak yalnızca 2xx response’ları kabul eder.
4xx ya da 5xx dönerse → HttpClientErrorException fırlatır.

 */