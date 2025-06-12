package tr.edu.ogu.birfaturainvoiceintegration.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${birfatura.api.base-url}")
    private String baseUrl;

    @Value("${birfatura.api.key}")
    private String apiKey;

    @Value("${birfatura.secret.key}")
    private String secretKey;

    @Value("${birfatura.integration.key}")
    private String integrationKey;

    @Bean
    public RestClient birFaturaRestClient() {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("X-Api-Key", apiKey)
                .defaultHeader("X-Secret-Key", secretKey)
                .defaultHeader("X-Integration-Key", integrationKey)
                .build();
    }
}

