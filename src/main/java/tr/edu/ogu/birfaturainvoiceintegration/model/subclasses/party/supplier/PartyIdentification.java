package tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.party.supplier;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartyIdentification")
public class PartyIdentification {

    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private ID id;

    @Data
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ID {
        @XmlValue
        private String value;

        @XmlAttribute(name = "schemeID")
        private String schemeID;
    }
}

