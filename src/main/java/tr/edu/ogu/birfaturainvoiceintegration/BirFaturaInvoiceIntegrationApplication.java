package tr.edu.ogu.birfaturainvoiceintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tr.edu.ogu.birfaturainvoiceintegration.util.FileUtil;
import tr.edu.ogu.birfaturainvoiceintegration.util.XmlUtil;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootApplication
public class BirFaturaInvoiceIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(BirFaturaInvoiceIntegrationApplication.class, args);

//        XmlUtil.createExampleXmlZipAndEncode();
    }



}
