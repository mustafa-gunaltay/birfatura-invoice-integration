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
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class XmlUtil {

    public static File createInvoiceXml(
            String currencyCode,
            String invoiceTypeCode,
            String noteText,
            String supplierVkn,
            String supplierName,
            String supplierStreet,

            String supplierBuildingNumber,
            String supplierSubdivisionName,
            String supplierCity,
            String supplierPostalCode,

            String supplierEmail,
            String customerTckn,
            String customerFirstName,
            String customerLastName,
            String customerStreet,

            String customerBuildingNumber,
            String customerSubdivisionName,
            String customerCity,
            String customerPostalCode,
            String itemName,
            String quantity,
            String unitPrice,
            int taxPercent,

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
        invoice.setCustomizationID("TR1.2"); // static for now
        invoice.setProfileID("EARSIVFATURA"); // static for now
        invoice.setId("ARS2024000000002"); // static for now
        invoice.setCopyIndicator(false);
        invoice.setUuid(UUID.randomUUID().toString());
        invoice.setIssueDate(LocalDate.now().toString());

        OffsetTime now = OffsetTime.now(ZoneOffset.ofHours(3));
        String formattedTime = now.format(DateTimeFormatter.ofPattern("HH:mm:ssxxx"));
        invoice.setIssueTime(formattedTime);

        invoice.setInvoiceTypeCode(invoiceTypeCode);
        invoice.setDocumentCurrencyCode(currencyCode);
        invoice.setLineCountNumeric(1);

        // Note alanı - XML'deki formata uygun
        invoice.setNote(Arrays.asList(noteText));

        // Additional Document References - XML'deki sırayla aynı
        List<AdditionalDocumentReference> additionalRefs = Arrays.asList(
                new AdditionalDocumentReference("123456", LocalDate.now().toString(), "CUST_INV_ID", null),
                new AdditionalDocumentReference("0100", LocalDate.now().toString(), "OUTPUT_TYPE", null),
                new AdditionalDocumentReference("99", LocalDate.now().toString(), "TRANSPORT_TYPE", null),
                new AdditionalDocumentReference("ELEKTRONIK", LocalDate.now().toString(), "EREPSENDT", null),
                new AdditionalDocumentReference("0", LocalDate.now().toString(), "SendingType", "KAGIT"),
                new AdditionalDocumentReference("FIT2024000000001", LocalDate.now().toString(), null, "XSLT")
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
        Country supplierCountry = new Country("Türkiye"); // static for now
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
        TaxScheme supplierTaxScheme = new TaxScheme("KDV", "0015"); // static for now
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
        CustomerPartyIdentification.ID customerIdObj = new CustomerPartyIdentification.ID(customerTckn, "TCKN");
        CustomerPartyIdentification customerIdentification = new CustomerPartyIdentification(customerIdObj);
        customerParty.setPartyIdentification(customerIdentification);




        // Customer Address - müşteri bilgilerini kullan, supplier değil
        Country customerCountry = new Country("Türkiye"); // static for now
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
        BigDecimal formattedUnitPrice = new BigDecimal(unitPrice);
        BigDecimal taxableAmount = formattedUnitPrice.setScale(2, RoundingMode.HALF_UP);
        BigDecimal taxAmount = taxableAmount
                .multiply(BigDecimal.valueOf(Integer.parseInt(quantity)))
                .multiply(BigDecimal.valueOf(taxPercent))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        // Tax Total
        Amount totalTaxAmount = new Amount(currencyCode, taxAmount);

        // Tax Scheme
        TaxScheme taxScheme = new TaxScheme("KDV", "0015"); // static for now
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
        BigDecimal lineExtensionAmount = taxableAmount.multiply(BigDecimal.valueOf(Integer.parseInt(quantity)));
        BigDecimal taxExclusiveAmount = lineExtensionAmount;
        BigDecimal taxInclusiveAmount = taxableAmount.multiply(BigDecimal.valueOf(Integer.parseInt(quantity))).add(taxAmount);
        BigDecimal payableAmount = taxInclusiveAmount;

        LegalMonetaryTotal legalMonetaryTotal = new LegalMonetaryTotal(
                new Amount(currencyCode, lineExtensionAmount),
                new Amount(currencyCode, taxExclusiveAmount),
                new Amount(currencyCode, taxInclusiveAmount),
                new Amount(currencyCode, BigDecimal.ZERO), // AllowanceTotalAmount is 0 because we suppose there is no discount
                new Amount(currencyCode, payableAmount)
        );
        invoice.setLegalMonetaryTotal(legalMonetaryTotal);

        // Invoice Line
        InvoiceLine.Quantity formattedQuantity = new InvoiceLine.Quantity("NIU", new BigDecimal(quantity));
        InvoiceLine invoiceLine = new InvoiceLine();
        invoiceLine.setId("1");
        invoiceLine.setInvoicedQuantity(formattedQuantity);
        invoiceLine.setLineExtensionAmount(new Amount(currencyCode, lineExtensionAmount));
        invoiceLine.setTaxTotal(taxTotal);

        // Item
        Item item = new Item(itemName);
        invoiceLine.setItem(item);

        // Price
        Price price = new Price(new Amount(currencyCode, formattedUnitPrice));
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

    public static void createExampleXmlZipAndEncode(){
        try {
            // XML'deki değerlerden alınan test parametreleri
            File xmlFile = XmlUtil.createInvoiceXml(

                    // Currency Code - XML'den alınan
                    "TRY",

                    // Invoice Type Code - XML'den alınan
                    "SATIS",

                    // Note Text - XML'den alınan
                    "",

                    // Supplier VKN - XML'den alınan
                    "1234567801",

                    // Supplier Name - XML'den alınan
                    "Test Firma",

                    // Supplier Street - XML'den alınan
                    "Deneme Sokak",

                    // Supplier Building Number - XML'den alınan
                    "12",

                    // Supplier Subdivision Name - XML'den alınan
                    "Çankaya",

                    // Supplier City - XML'den alınan
                    "Ankara",

                    // Supplier Postal Code - XML'den alınan
                    "06500",

                    // Supplier Email - XML'den alınan
                    "info@firma.com",

                    // Customer TCKN - XML'den alınan
                    "11111111111",

                    // Customer First Name - XML'den alınan
                    "Ali",

                    // Customer Last Name - XML'den alınan
                    "Yılmaz",

                    // Customer Street - XML'den alınan
                    "Alici Sokak",

                    // Customer Building Number - XML'den alınan
                    "5",

                    // Customer Subdivision Name - XML'den alınan
                    "Üsküdar",

                    // Customer City - XML'den alınan
                    "İstanbul",

                    // Customer Postal Code - XML'den alınan
                    "34000",

                    // Item Name - XML'den alınan
                    "Ürün Açıklaması",

                    // Quantity - XML'den alınan
                    "1.000",

                    // Unit Price - XML'den alınan
                    "100.00",

                    // Tax Percent - XML'den alınan
                    18,

                    // File Name
                    "test_invoice.xml"
            );

            System.out.println("XML dosyası başarıyla oluşturuldu!");
            System.out.println("Dosya yolu: " + xmlFile.getAbsolutePath());
            System.out.println("Dosya boyutu: " + xmlFile.length() + " bytes");

            if (xmlFile.exists()) {
                System.out.println("✅ Dosya başarıyla oluşturuldu ve mevcut.");
            } else {
                System.out.println("❌ Dosya oluşturulamadı!");
            }

            System.out.println("Dosyanin ziplenmis ve base64'e gore encode edilmis stringi:");
            String base64EncodedZip = FileUtil.zipAndEncode(xmlFile);
            System.out.println(base64EncodedZip);


        } catch (Exception e) {
            System.err.println("Hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}