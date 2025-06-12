package tr.edu.ogu.birfaturainvoiceintegration.model.subclasses.extension;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UBLExtension")
@Data
public class UBLExtension {

    @XmlElement(name = "ExtensionContent", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    private ExtensionContent extensionContent;
}