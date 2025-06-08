package tr.edu.ogu.birfaturainvoiceintegration.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import tr.edu.ogu.birfaturainvoiceintegration.dto.SendDocumentRequest;
import tr.edu.ogu.birfaturainvoiceintegration.dto.response.ApiResponse;
import tr.edu.ogu.birfaturainvoiceintegration.dto.response.SendDocumentResult;

@Service
@RequiredArgsConstructor
public class SendDocumentService {

    private final RestClient birFaturaRestClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ApiResponse<SendDocumentResult> sendDocument(SendDocumentRequest request) {
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
}

/*
------ 1 ------
retrieve() default olarak yalnızca 2xx response’ları kabul eder.
4xx ya da 5xx dönerse → HttpClientErrorException fırlatır.



 */