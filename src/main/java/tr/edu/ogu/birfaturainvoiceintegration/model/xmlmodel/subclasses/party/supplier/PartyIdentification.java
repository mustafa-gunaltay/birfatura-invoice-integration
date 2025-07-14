package tr.edu.ogu.birfaturainvoiceintegration.model.xmlmodel.subclasses.party.supplier;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplierPartyIdentification")
public class PartyIdentification {

    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private ID id;

    @Data
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "SupplierIDType") // Farklı type name
    public static class ID {
        @XmlValue
        private String value;

        @XmlAttribute(name = "schemeID")
        private String schemeID;
    }
}
