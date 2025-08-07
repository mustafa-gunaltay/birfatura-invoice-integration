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
import tr.edu.ogu.birfaturainvoiceintegration.model.dbmodel.Customer;
import tr.edu.ogu.birfaturainvoiceintegration.model.dbmodel.Invoice;
import tr.edu.ogu.birfaturainvoiceintegration.model.dbmodel.InvoiceItem;
import tr.edu.ogu.birfaturainvoiceintegration.model.dbmodel.Supplier;
import tr.edu.ogu.birfaturainvoiceintegration.repository.CustomerRepository;
import tr.edu.ogu.birfaturainvoiceintegration.repository.InvoiceItemRepository;
import tr.edu.ogu.birfaturainvoiceintegration.repository.InvoiceRepository;
import tr.edu.ogu.birfaturainvoiceintegration.repository.SupplierRepository;
import tr.edu.ogu.birfaturainvoiceintegration.util.FileUtil;
import tr.edu.ogu.birfaturainvoiceintegration.util.XmlUtil;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SendDocumentService {

    private final RestClient birFaturaRestClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final SupplierRepository supplierRepository;
    private final InvoiceItemRepository invoiceItemRepository;

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

        UUID randomUUID = UUID.randomUUID();
        LocalDate now = LocalDate.now();
        OffsetTime nowTime = OffsetTime.now(ZoneOffset.ofHours(3));
        try {
            File xmlFile = XmlUtil.createInvoiceXml(
                    randomUUID,
                    now,
                    nowTime,

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


        ApiResponse<SendDocumentResult> apiResponse = sendDocument(apiRequest);
        if (apiResponse.isSuccess()) {
            // 1. Supplier
            Supplier supplier = new Supplier();
            supplier.setTaxNumber(request.getSupplierVkn());
            supplier.setName(request.getSupplierName());
            supplier.setEmail(request.getSupplierEmail());
            supplier.setStreet(request.getSupplierStreet());
            supplier.setBuildingNo(request.getSupplierBuildingNumber());
            supplier.setDistrict(request.getSupplierSubdivisionName());
            supplier.setCity(request.getSupplierCity());
            supplier.setPostalCode(request.getSupplierPostalCode());
            supplierRepository.save(supplier);

            // 2. Customer
            Customer customer = new Customer();
            customer.setIdNumber(request.getCustomerTckn());
            customer.setFirstName(request.getCustomerFirstName());
            customer.setLastName(request.getCustomerLastName());
            customer.setStreet(request.getCustomerStreet());
            customer.setBuildingNo(request.getCustomerBuildingNumber());
            customer.setDistrict(request.getSupplierSubdivisionName()); // dikkat: doğru alan mı?
            customer.setCity(request.getCustomerCity());
            customer.setPostalCode(request.getCustomerPostalCode());
            customerRepository.save(customer);

            // 3. InvoiceItem
            InvoiceItem item = new InvoiceItem();
            item.setProductName(request.getItemName());
            item.setQuantity(Double.parseDouble(request.getQuantity()));
            item.setUnitPrice(Double.parseDouble(request.getUnitPrice()));
            item.setTaxRate( (double) request.getTaxPercent());
            invoiceItemRepository.save(item);

            // 4. Invoice
            Invoice invoice = new Invoice();
            invoice.setUuid(randomUUID);
            invoice.setInvoiceDate(now);
            invoice.setInvoiceTime(nowTime.toLocalTime());
            invoice.setPdfUrl(apiResponse.getResult().getPdfLink()); // gelen PDF linki varsa (eger varsa)
            invoice.setNote(request.getNoteText());
            invoice.setSupplier(supplier);
            invoice.setCustomer(customer);
            invoice.setInvoiceItem(item);

            // 5. Save
            invoiceRepository.save(invoice);
        }

        return apiResponse;
    }
}

/* Notes
------ 1 ------
retrieve() default olarak yalnızca 2xx response’ları kabul eder.
4xx ya da 5xx dönerse → HttpClientErrorException fırlatır.

 */