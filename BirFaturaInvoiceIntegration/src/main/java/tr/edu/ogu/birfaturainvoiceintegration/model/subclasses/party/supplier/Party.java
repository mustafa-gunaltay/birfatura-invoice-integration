package tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.party.supplier;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Party", propOrder = {
        "websiteURI",
        "partyIdentifications",
        "partyName",
        "postalAddress",
        "partyTaxScheme",
        "contact"
})
@Data
public class Party {

    @XmlElement(name = "WebsiteURI", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String websiteURI;

    @XmlElement(name = "PartyIdentification", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private PartyIdentification partyIdentifications;

    @XmlElement(name = "PartyName", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private PartyName partyName;

    @XmlElement(name = "PostalAddress", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private Address postalAddress;

    @XmlElement(name = "PartyTaxScheme", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private PartyTaxScheme partyTaxScheme;

    @XmlElement(name = "Contact", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private Contact contact;
}
