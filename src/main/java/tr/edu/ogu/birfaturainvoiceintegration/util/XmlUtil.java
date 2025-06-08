package tr.edu.ogu.birfaturainvoiceintegration.util;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import tr.edu.ogu.birfaturainvoiceintegration.model.*;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.document.AdditionalDocumentReference;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.extension.ExtensionContent;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.extension.UBLExtension;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.extension.UBLExtensions;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.line.InvoiceLine;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.line.Item;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.line.Price;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.monetary.LegalMonetaryTotal;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.party.customer.AccountingCustomerParty;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.party.customer.CustomerParty;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.party.customer.CustomerPartyIdentification;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.party.customer.Person;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.party.supplier.*;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.tax.*;
import tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.tax.TaxScheme;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class XmlUtil {

    public static File createInvoiceXml(
            String uuid,
            String invoiceId,
            LocalDate issueDate,
            String issueTime,
            String currencyCode,
            String profileId,
            String customizationId,
            String invoiceTypeCode,
            String noteText,
            String supplierVkn,
            String supplierName,
            String supplierStreet,
            String supplierBuildingName,
            String supplierBuildingNumber,
            String supplierSubdivisionName,
            String supplierCity,
            String supplierPostalCode,
            String supplierTaxOffice,
            String supplierEmail,
            String customerTckn,
            String customerFirstName,
            String customerLastName,
            String customerStreet,
            String customerBuildingName,
            String customerBuildingNumber,
            String customerSubdivisionName,
            String customerCity,
            String customerPostalCode,
            String itemName,
            BigDecimal quantity,
            BigDecimal unitPrice,
            int taxPercent,
            String outputXsltCode,
            String fileName
    ) throws Exception {

        Invoice invoice = new Invoice();
        invoice.setUblVersionID("2.1");
        invoice.setCustomizationID(customizationId);
        invoice.setProfileID(profileId);
        invoice.setId(invoiceId);
        invoice.setCopyIndicator(false);
        invoice.setUuid(uuid);
        invoice.setIssueDate(issueDate.toString());
        invoice.setIssueTime(issueTime);
        invoice.setInvoiceTypeCode(invoiceTypeCode);
        invoice.setDocumentCurrencyCode(currencyCode);
        invoice.setLineCountNumeric(1);
        invoice.setNote(Collections.singletonList(noteText)); //

        // AdditionalDocumentReference
        AdditionalDocumentReference xsltDoc = new AdditionalDocumentReference("FIT2024000000001", issueDate.toString(), null, "XSLT");
        invoice.setAdditionalDocumentReference(List.of(
                new AdditionalDocumentReference(uuid, issueDate.toString(), null, "CUST_INV_ID"),
                new AdditionalDocumentReference("0100", issueDate.toString(), null,"OUTPUT_TYPE"),
                new AdditionalDocumentReference("99", issueDate.toString(), null,"TRANSPORT_TYPE"),
                new AdditionalDocumentReference("ELEKTRONIK", issueDate.toString(), null,"EREPSENDT"),
                new AdditionalDocumentReference("0", issueDate.toString(), "SendingType", "KAGIT"),
                xsltDoc
        ));

        // Supplier
        Party supplierParty = new Party();
        supplierParty.setPartyIdentifications(
                new PartyIdentification(new PartyIdentification.ID(supplierVkn, "VKN"))
        );


        supplierParty.setPartyName(new PartyName(supplierName));
        supplierParty.setPostalAddress(new Address(supplierStreet, supplierBuildingNumber, supplierSubdivisionName, supplierCity, supplierPostalCode, new Country("Turkiye")));
        supplierParty.setPartyTaxScheme(new PartyTaxScheme(new tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.party.supplier.TaxScheme("Ankara")));
        supplierParty.setContact(new Contact(null, supplierEmail));
        invoice.setAccountingSupplierParty(new AccountingSupplierParty(supplierParty));

        // Customer
        CustomerParty customer = new CustomerParty();
        customer.setPartyIdentification(new CustomerPartyIdentification("TCKN", customerTckn));
        customer.setPostalAddress(new Address(supplierStreet, supplierBuildingNumber, supplierSubdivisionName, supplierCity, supplierPostalCode, new Country("Turkiye")));
        customer.setPerson(new Person(customerFirstName, customerLastName));
        invoice.setAccountingCustomerParty(new AccountingCustomerParty(customer));

        // TaxTotal
        BigDecimal taxAmount = unitPrice.multiply(BigDecimal.valueOf(taxPercent)).divide(BigDecimal.valueOf(100));
        Amount totalTaxAmount = new Amount(currencyCode, taxAmount);
        TaxScheme taxScheme = new TaxScheme("KDV", "0015");
        TaxCategory taxCategory = new TaxCategory(taxScheme);
        TaxSubtotal taxSubtotal = new TaxSubtotal(new Amount(currencyCode, unitPrice), new Amount(currencyCode, taxAmount), taxPercent, taxCategory);
        TaxTotal taxTotal = new TaxTotal(totalTaxAmount, taxSubtotal);
        invoice.setTaxTotal(taxTotal);

        // LegalMonetaryTotal
        BigDecimal totalAmount = unitPrice.add(taxAmount);
        LegalMonetaryTotal monetaryTotal = new LegalMonetaryTotal(
                new Amount(currencyCode, unitPrice),
                new Amount(currencyCode, unitPrice),
                new Amount(currencyCode, totalAmount),
                new Amount(currencyCode, totalAmount),
                new Amount(currencyCode, totalAmount)
        );
        invoice.setLegalMonetaryTotal(monetaryTotal);

        // InvoiceLine
        InvoiceLine line = new InvoiceLine();
        line.setId("1");
        line.setInvoicedQuantity(quantity);
        line.setLineExtensionAmount(new Amount(currencyCode, unitPrice));
        line.setItem(new Item(null, itemName));
        line.setPrice(new Price(new Amount(currencyCode, unitPrice)));
        invoice.setInvoiceLines(List.of(line));

        // UBL Extensions
        UBLExtension ext = new UBLExtension();
        ext.setExtensionContent(new ExtensionContent());
        UBLExtensions exts = new UBLExtensions();
        exts.setUblExtension(List.of(ext));
        invoice.setUblExtensions(exts);

        // Write to XML
        File file = new File("invoices/" + fileName);
        file.getParentFile().mkdirs();

        JAXBContext context = JAXBContext.newInstance(Invoice.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(invoice, file);

        return file;
    }
}

