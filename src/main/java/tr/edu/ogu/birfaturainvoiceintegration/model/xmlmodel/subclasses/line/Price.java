package tr.edu.ogu.birfaturainvoiceintegration.model.xmlmodel.subclasses.line;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import tr.edu.ogu.birfaturainvoiceintegration.model.xmlmodel.subclasses.tax.Amount;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Price")
@Data
@AllArgsConstructor
public class Price {

    @XmlElement(name = "PriceAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private Amount priceAmount;
}

