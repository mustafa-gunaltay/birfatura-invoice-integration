package tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.party.customer;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.party.supplier.Party;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountingCustomerParty")
@Data
@AllArgsConstructor
public class AccountingCustomerParty {

    @XmlElement(name = "Party", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private CustomerParty party;
}

