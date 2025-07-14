package tr.edu.ogu.birfaturainvoiceintegration.model.xmlmodel.subclasses.party.customer;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;

//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(name = "CustomerPartyIdentification")
//@Data
//@AllArgsConstructor
//public class CustomerPartyIdentification {
//
//    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
//    private String ID;
//
//    @XmlAttribute(name = "schemeID")
//    private String schemeID;
//
//
//}


// PartyIdentification'a bakarak denedigim 2. versiyon

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerPartyIdentification")
@Data
@AllArgsConstructor
public class CustomerPartyIdentification {

    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private ID id;

    @Data
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "CustomerIDType") // Farklı type name
    public static class ID {
        @XmlValue
        private String value;

        @XmlAttribute(name = "schemeID")
        private String schemeID;
    }
}
