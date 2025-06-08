package tr.edu.ogu.birfaturainvoiceintegration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SendDocumentRequest {

    @Schema(description = "Alıcının etiket bilgisi")
    @JsonProperty("receiverTag")
    private String receiverTag;

    @Schema(description = "Base64 ziplenmiş fatura XML içeriği")
    @JsonProperty("documentBytes")
    private String documentBytes;

    @Schema(description = "Belge numarası sistem tarafından otomatik verilsin mi")
    @JsonProperty("isDocumentNoAuto")
    private boolean isDocumentNoAuto;

    @Schema(description = "Belge türü (örn: EARSIV)")
    @JsonProperty("systemTypeCodes")
    private String systemTypeCodes;
}


