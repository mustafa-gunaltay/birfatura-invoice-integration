package tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.line;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.tax.Amount;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.tax.TaxTotal;

import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceLine")
@Data
public class InvoiceLine {

    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String id;

    @XmlElement(name = "InvoicedQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private Quantity invoicedQuantity;

    @XmlElement(name = "LineExtensionAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private Amount lineExtensionAmount;

    @XmlElement(name = "TaxTotal", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private TaxTotal taxTotal;

    @XmlElement(name = "Item", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private Item item;

    @XmlElement(name = "Price", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private Price price;

    // Quantity sınıfı iç içe sınıf olarak tanımlanıyor
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "Quantity")
    @Data
    public static class Quantity {
        @XmlAttribute(name = "unitCode")
        private String unitCode;

        @XmlValue
        private BigDecimal value;
    }
}

