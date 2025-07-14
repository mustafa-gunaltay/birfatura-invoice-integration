package tr.edu.ogu.birfaturainvoiceintegration.model.xmlmodel.subclasses.extension;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UBLExtensions")
@Data
public class UBLExtensions {

    @XmlElement(name = "UBLExtension", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    private UBLExtension ublExtension;
}


