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
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class XmlUtil {

    public static File createInvoiceXml(
            String uuid,
            String invoiceId,
            LocalDate issueDate,
            String issueTimeStr,
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

        // Ana Invoice nesnesini oluştur
        Invoice invoice = new Invoice();

        // UBL Extensions - XML'de ilk sırada olmalı
        UBLExtension extension = new UBLExtension();
        extension.setExtensionContent(new ExtensionContent());
        UBLExtensions extensions = new UBLExtensions();
        extensions.setUblExtension(extension);
        invoice.setUblExtensions(extensions);

        // Temel fatura bilgileri
        invoice.setUblVersionID("2.1");
        invoice.setCustomizationID(customizationId);
        invoice.setProfileID(profileId);
        invoice.setId(invoiceId);
        invoice.setCopyIndicator(false);
        invoice.setUuid(uuid);
        invoice.setIssueDate(issueDate.toString());
        invoice.setIssueTime(issueTimeStr);
        invoice.setInvoiceTypeCode(invoiceTypeCode);
        invoice.setDocumentCurrencyCode(currencyCode);
        invoice.setLineCountNumeric(1);

        // Note alanı - XML'deki formata uygun
        invoice.setNote(Arrays.asList(noteText));

        // Additional Document References - XML'deki sırayla aynı
        List<AdditionalDocumentReference> additionalRefs = Arrays.asList(
                new AdditionalDocumentReference("123456", issueDate.toString(), "CUST_INV_ID", null),
                new AdditionalDocumentReference("0100", issueDate.toString(), "OUTPUT_TYPE", null),
                new AdditionalDocumentReference("99", issueDate.toString(), "TRANSPORT_TYPE", null),
                new AdditionalDocumentReference("ELEKTRONIK", issueDate.toString(), "EREPSENDT", null),
                new AdditionalDocumentReference("0", issueDate.toString(), "SendingType", "KAGIT"),
                new AdditionalDocumentReference("FIT2024000000001", issueDate.toString(), null, "XSLT")
        );
        invoice.setAdditionalDocumentReference(additionalRefs);

        // Supplier (Satıcı) bilgileri
        Party supplierParty = new Party();

        // Supplier Party Identification
        PartyIdentification.ID supplierIdObj = new PartyIdentification.ID(supplierVkn, "VKN");
        PartyIdentification supplierIdentification = new PartyIdentification(supplierIdObj);
        supplierParty.setPartyIdentifications(supplierIdentification);

        // Supplier Party Name
        supplierParty.setPartyName(new PartyName(supplierName));

        // Supplier Address
        Country supplierCountry = new Country("Türkiye");
        Address supplierAddress = new Address(
                supplierStreet,
                supplierBuildingNumber,
                supplierSubdivisionName,
                supplierCity,
                supplierPostalCode,
                supplierCountry
        );
        supplierParty.setPostalAddress(supplierAddress);

        // Supplier Tax Scheme
        TaxScheme supplierTaxScheme = new TaxScheme("KDV", "0015");
        PartyTaxScheme partyTaxScheme = new PartyTaxScheme(supplierTaxScheme);
        supplierParty.setPartyTaxScheme(partyTaxScheme);

        // Supplier Contact
        Contact supplierContact = new Contact(null, supplierEmail);
        supplierParty.setContact(supplierContact);

        // AccountingSupplierParty'yi set et
        AccountingSupplierParty accountingSupplierParty = new AccountingSupplierParty(supplierParty);
        invoice.setAccountingSupplierParty(accountingSupplierParty);

        // Customer (Müşteri) bilgileri
        CustomerParty customerParty = new CustomerParty();

        // Customer Party Identification
        CustomerPartyIdentification customerIdentification = new CustomerPartyIdentification("TCKN", customerTckn);
        customerParty.setPartyIdentification(customerIdentification);

        // Customer Address - müşteri bilgilerini kullan, supplier değil
        Country customerCountry = new Country("Türkiye");
        Address customerAddress = new Address(
                customerStreet,
                customerBuildingNumber,
                customerSubdivisionName,
                customerCity,
                customerPostalCode,
                customerCountry
        );
        customerParty.setPostalAddress(customerAddress);

        // Customer Person
        Person customerPerson = new Person(customerFirstName, customerLastName);
        customerParty.setPerson(customerPerson);

        // AccountingCustomerParty'yi set et
        AccountingCustomerParty accountingCustomerParty = new AccountingCustomerParty(customerParty);
        invoice.setAccountingCustomerParty(accountingCustomerParty);

        // Tax hesaplamaları
        BigDecimal taxableAmount = unitPrice.setScale(2, RoundingMode.HALF_UP);
        BigDecimal taxAmount = taxableAmount
                .multiply(BigDecimal.valueOf(taxPercent))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        // Tax Total
        Amount totalTaxAmount = new Amount(currencyCode, taxAmount);

        // Tax Scheme
        TaxScheme taxScheme = new TaxScheme("KDV", "0015");
        TaxCategory taxCategory = new TaxCategory(taxScheme);

        // Tax Subtotal
        TaxSubtotal taxSubtotal = new TaxSubtotal(
                new Amount(currencyCode, taxableAmount),
                new Amount(currencyCode, taxAmount),
                BigDecimal.valueOf(taxPercent),
                taxCategory
        );

        TaxTotal taxTotal = new TaxTotal(totalTaxAmount, taxSubtotal);
        invoice.setTaxTotal(taxTotal);

        // Legal Monetary Total
        BigDecimal lineExtensionAmount = taxableAmount;
        BigDecimal taxExclusiveAmount = taxableAmount;
        BigDecimal taxInclusiveAmount = taxableAmount.add(taxAmount);
        BigDecimal payableAmount = taxInclusiveAmount;

        LegalMonetaryTotal legalMonetaryTotal = new LegalMonetaryTotal(
                new Amount(currencyCode, lineExtensionAmount),
                new Amount(currencyCode, taxExclusiveAmount),
                new Amount(currencyCode, taxInclusiveAmount),
                new Amount(currencyCode, payableAmount),
                new Amount(currencyCode, payableAmount) // AllowanceTotalAmount için aynı değer
        );
        invoice.setLegalMonetaryTotal(legalMonetaryTotal);

        // Invoice Line
        InvoiceLine.Quantity formattedQuantity = new InvoiceLine.Quantity("NIU", BigDecimal.ONE);
        InvoiceLine invoiceLine = new InvoiceLine();
        invoiceLine.setId("1");
        invoiceLine.setInvoicedQuantity(formattedQuantity);
        invoiceLine.setLineExtensionAmount(new Amount(currencyCode, lineExtensionAmount));

        // Item
        Item item = new Item(itemName);
        invoiceLine.setItem(item);

        // Price
        Price price = new Price(new Amount(currencyCode, unitPrice));
        invoiceLine.setPrice(price);

        invoice.setInvoiceLine(invoiceLine);

        // XML dosyasını oluştur
        File file = new File("invoices/" + fileName);
        file.getParentFile().mkdirs();

        JAXBContext context = JAXBContext.newInstance(Invoice.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        marshaller.marshal(invoice, file);
        return file;
    }
}