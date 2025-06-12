package tr.edu.ogu.birfaturainvoiceintegration.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ApiResponse<T> {

    @JsonProperty("Success")
    @Schema(description = "İşlem başarılı mı?")
    private boolean success;

    @JsonProperty("Message")
    @Schema(description = "API mesajı")
    private String message;

    @JsonProperty("Code")
    @Schema(description = "Yanıt kodu")
    private String code;

    @JsonProperty("Result")
    @Schema(description = "Yanıt sonucu (fatura no, pdf linki vs.)")
    private T result;

}

