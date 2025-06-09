package tr.edu.ogu.birfaturainvoiceintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tr.edu.ogu.birfaturainvoiceintegration.util.XmlUtil;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootApplication
public class BirFaturaInvoiceIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(BirFaturaInvoiceIntegrationApplication.class, args);

        try {
            // XML'deki değerlerden alınan test parametreleri
            File xmlFile = XmlUtil.createInvoiceXml(
                    // UUID - XML'den alınan
                    "4d3ec158-2cd4-4a5e-8182-9c301122f001",

                    // Invoice ID - XML'den alınan
                    "ARS2024000000001",

                    // Issue Date - XML'den alınan
                    LocalDate.of(2025, 6, 6),

                    // Issue Time - XML'den alınan
                    "14:35:00+03:00",

                    // Currency Code - XML'den alınan
                    "TRY",

                    // Profile ID - XML'den alınan
                    "EARSIVFATURA",

                    // Customization ID - XML'den alınan
                    "TR1.2",

                    // Invoice Type Code - XML'den alınan
                    "SATIS",

                    // Note Text - XML'den alınan
                    "Yalnız Yüz Lira",

                    // Supplier VKN - XML'den alınan
                    "1234567801",

                    // Supplier Name - XML'den alınan
                    "Test Firma",

                    // Supplier Street - XML'den alınan
                    "Deneme Sokak",

                    // Supplier Building Name - XML'den alınan
                    "Test Apartmanı",

                    // Supplier Building Number - XML'den alınan
                    "12",

                    // Supplier Subdivision Name - XML'den alınan
                    "Çankaya",

                    // Supplier City - XML'den alınan
                    "Ankara",

                    // Supplier Postal Code - XML'den alınan
                    "06500",

                    // Supplier Tax Office - XML'den alınan
                    "ANKARA",

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

                    // Customer Building Name - XML'den alınan
                    "A Blok",

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
                    new BigDecimal("1.000"),

                    // Unit Price - XML'den alınan
                    new BigDecimal("100.00"),

                    // Tax Percent - XML'den alınan
                    18,

                    // Output XSLT Code
                    "FIT2024000000001",

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

        } catch (Exception e) {
            System.err.println("Hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
