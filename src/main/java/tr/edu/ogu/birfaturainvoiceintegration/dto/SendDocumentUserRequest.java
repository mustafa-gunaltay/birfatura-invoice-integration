package tr.edu.ogu.birfaturainvoiceintegration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SendDocumentUserRequest {

    @Schema(description = "Para birimi (örneğin: TRY, USD)")
    @JsonProperty("currencyCode")
    private String currencyCode;

    @Schema(description = "Fatura tipi kodu (örneğin: SATIS, IADE)")
    @JsonProperty("invoiceTypeCode")
    private String invoiceTypeCode;

    @Schema(description = "Fatura not metni (örneğin: Yalnız Yüz Lira)")
    @JsonProperty("noteText")
    private String noteText;

    @Schema(description = "Tedarikçi VKN bilgisi")
    @JsonProperty("supplierVkn")
    private String supplierVkn;

    @Schema(description = "Tedarikçi adı")
    @JsonProperty("supplierName")
    private String supplierName;

    @Schema(description = "Tedarikçi sokak bilgisi")
    @JsonProperty("supplierStreet")
    private String supplierStreet;

    @Schema(description = "Tedarikçi bina numarası")
    @JsonProperty("supplierBuildingNumber")
    private String supplierBuildingNumber;

    @Schema(description = "Tedarikçi mahalle/semt bilgisi")
    @JsonProperty("supplierSubdivisionName")
    private String supplierSubdivisionName;

    @Schema(description = "Tedarikçi şehir bilgisi")
    @JsonProperty("supplierCity")
    private String supplierCity;

    @Schema(description = "Tedarikçi posta kodu")
    @JsonProperty("supplierPostalCode")
    private String supplierPostalCode;

    @Schema(description = "Tedarikçi e-posta adresi")
    @JsonProperty("supplierEmail")
    private String supplierEmail;

    @Schema(description = "Müşteri TCKN bilgisi")
    @JsonProperty("customerTckn")
    private String customerTckn;

    @Schema(description = "Müşteri adı")
    @JsonProperty("customerFirstName")
    private String customerFirstName;

    @Schema(description = "Müşteri soyadı")
    @JsonProperty("customerLastName")
    private String customerLastName;

    @Schema(description = "Müşteri sokak bilgisi")
    @JsonProperty("customerStreet")
    private String customerStreet;

    @Schema(description = "Müşteri bina numarası")
    @JsonProperty("customerBuildingNumber")
    private String customerBuildingNumber;

    @Schema(description = "Müşteri mahalle/semt bilgisi")
    @JsonProperty("customerSubdivisionName")
    private String customerSubdivisionName;

    @Schema(description = "Müşteri şehir bilgisi")
    @JsonProperty("customerCity")
    private String customerCity;

    @Schema(description = "Müşteri posta kodu")
    @JsonProperty("customerPostalCode")
    private String customerPostalCode;

    @Schema(description = "Ürün adı veya açıklaması")
    @JsonProperty("itemName")
    private String itemName;

    @Schema(description = "Ürün adedi")
    @JsonProperty("quantity")
    private String quantity;

    @Schema(description = "Birim fiyat")
    @JsonProperty("unitPrice")
    private String unitPrice;

    @Schema(description = "Vergi oranı (%)")
    @JsonProperty("taxPercent")
    private int taxPercent;

}