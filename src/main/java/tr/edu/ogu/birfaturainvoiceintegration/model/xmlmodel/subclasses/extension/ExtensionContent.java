package tr.edu.ogu.birfaturainvoiceintegration.model.xmlmodel.subclasses.extension;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExtensionContent")
@Data
public class ExtensionContent {
    // Boş bırakılabilir, çünkü içerik çoğunlukla imza ile doldurulur.
}