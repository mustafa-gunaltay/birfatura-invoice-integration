package tr.edu.ogu.birfaturainvoiceintegration.model.xmlmodel.subclasses.tax;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
@AllArgsConstructor
public class Amount {

    @XmlAttribute(name = "currencyID")
    private String currencyID;

    @XmlValue
    private BigDecimal value;
}

