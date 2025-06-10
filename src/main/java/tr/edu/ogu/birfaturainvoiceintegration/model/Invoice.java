package tr.edu.ogu.birfaturainvoiceintegration.model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.document.AdditionalDocumentReference;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.extension.UBLExtensions;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.line.InvoiceLine;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.monetary.LegalMonetaryTotal;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.party.customer.AccountingCustomerParty;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.party.supplier.AccountingSupplierParty;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.tax.TaxTotal;
import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "Invoice", namespace = "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "ublExtensions", "ublVersionID", "customizationID", "profileID", "id", "copyIndicator",
        "uuid", "issueDate", "issueTime", "invoiceTypeCode", "documentCurrencyCode", "lineCountNumeric",
        "note", "additionalDocumentReference", "accountingSupplierParty", "accountingCustomerParty",
        "taxTotal", "legalMonetaryTotal", "invoiceLine"
})
@Data


public class Invoice {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    private UBLExtensions ublExtensions;

    @XmlElement(name = "UBLVersionID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String ublVersionID;

    @XmlElement(name = "CustomizationID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String customizationID;

    @XmlElement(name = "ProfileID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String profileID;

    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String id;

    @XmlElement(name = "CopyIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private Boolean copyIndicator;

    @XmlElement(name = "UUID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String uuid;

    @XmlElement(name = "IssueDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String issueDate;

    @XmlElement(name = "IssueTime", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String issueTime;

    @XmlElement(name = "InvoiceTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String invoiceTypeCode;

    @XmlElement(name = "DocumentCurrencyCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String documentCurrencyCode;

    @XmlElement(name = "LineCountNumeric", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private Integer lineCountNumeric;

    @XmlElement(name = "Note", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private List<String> note;

    @XmlElement(name = "AdditionalDocumentReference", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private List<AdditionalDocumentReference> additionalDocumentReference;

    @XmlElement(name = "AccountingSupplierParty", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private AccountingSupplierParty accountingSupplierParty;

    @XmlElement(name = "AccountingCustomerParty", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private AccountingCustomerParty accountingCustomerParty;

    @XmlElement(name = "TaxTotal", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private TaxTotal taxTotal;

    @XmlElement(name = "LegalMonetaryTotal", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private LegalMonetaryTotal legalMonetaryTotal;

    @XmlElement(name = "InvoiceLine", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private InvoiceLine invoiceLine; // birden fazla sey alindiysa List<InvoiceLine> seklinde tanimlanabilir


}
