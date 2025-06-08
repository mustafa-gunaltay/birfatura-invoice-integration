package tr.edu.ogu.birfaturainvoiceintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tr.edu.ogu.birfaturainvoiceintegration.util.XmlUtil;

import java.io.File;
import java.util.UUID;

@SpringBootApplication
public class BirFaturaInvoiceIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(BirFaturaInvoiceIntegrationApplication.class, args);

//        try {
//            String uuid = UUID.randomUUID().toString();
//            File xmlFile = XmlUtil.createInvoiceXml(uuid, "test-fatura.xml");
//
//            System.out.println("XML başarıyla oluşturuldu: " + xmlFile.getAbsolutePath());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
