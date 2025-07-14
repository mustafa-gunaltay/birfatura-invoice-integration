package tr.edu.ogu.birfaturainvoiceintegration.model.xmlmodel.subclasses.party.supplier;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartyName")
@Data
@AllArgsConstructor
public class PartyName {

    @XmlElement(name = "Name", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String name;
}

