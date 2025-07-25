package tr.edu.ogu.birfaturainvoiceintegration.model.xmlmodel.subclasses.party.supplier;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import tr.edu.ogu.birfaturainvoiceintegration.model.xmlmodel.subclasses.tax.TaxScheme;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartyTaxScheme")
@Data
@AllArgsConstructor
public class PartyTaxScheme {

    @XmlElement(name = "TaxScheme", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private TaxScheme taxScheme;
}
