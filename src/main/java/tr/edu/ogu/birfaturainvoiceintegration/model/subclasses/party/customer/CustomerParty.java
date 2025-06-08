package tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.party.customer;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.party.supplier.Address;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.party.supplier.Contact;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.party.supplier.PartyIdentification;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerParty")
@Data
public class CustomerParty {

    @XmlElement(name = "PartyIdentification", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private CustomerPartyIdentification partyIdentification;

    @XmlElement(name = "PostalAddress", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private Address postalAddress;

    @XmlElement(name = "Contact", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private Contact contact;

    @XmlElement(name = "Person", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private Person person;
}
