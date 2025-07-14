package tr.edu.ogu.birfaturainvoiceintegration.model.xmlmodel.subclasses.monetary;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import tr.edu.ogu.birfaturainvoiceintegration.model.xmlmodel.subclasses.tax.Amount;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LegalMonetaryTotal")
@Data
@AllArgsConstructor
public class LegalMonetaryTotal {

    @XmlElement(name = "LineExtensionAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private Amount lineExtensionAmount;

    @XmlElement(name = "TaxExclusiveAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private Amount taxExclusiveAmount;

    @XmlElement(name = "TaxInclusiveAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private Amount taxInclusiveAmount;

    @XmlElement(name = "AllowanceTotalAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private Amount allowanceTotalAmount;

    @XmlElement(name = "PayableAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private Amount payableAmount;
}

