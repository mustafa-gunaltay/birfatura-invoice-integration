package tr.edu.ogu.birfaturainvoiceintegration.model.xmlmodel.subclasses.tax;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxCategory")
@Data
@AllArgsConstructor
public class TaxCategory {

    @XmlElement(name = "TaxScheme", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private TaxScheme taxScheme;
}

