package tr.edu.ogu.birfaturainvoiceintegration.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SendDocumentResult {

    @JsonProperty("invoiceNo")
    @Schema(description = "Atanan belge numarası")
    private String invoiceNo;

    @JsonProperty("zipped")
    @Schema(description = "Belge zip içeriği (base64)")
    private String zipped;

    @JsonProperty("htmlString")
    @Schema(description = "HTML içerik")
    private String htmlString;

    @JsonProperty("pdfLink")
    @Schema(description = "PDF linki")
    private String pdfLink;

}
