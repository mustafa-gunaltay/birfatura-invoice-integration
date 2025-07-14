package tr.edu.ogu.birfaturainvoiceintegration.model.xmlmodel.subclasses.party.customer;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Person")
@Data
@AllArgsConstructor
public class Person {

    @XmlElement(name = "FirstName", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String firstName;

    @XmlElement(name = "FamilyName", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String familyName;
}

