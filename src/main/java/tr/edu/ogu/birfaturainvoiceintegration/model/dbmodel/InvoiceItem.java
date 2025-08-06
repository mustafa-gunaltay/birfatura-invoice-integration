package tr.edu.ogu.birfaturainvoiceintegration.model.dbmodel;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "invoice_item")
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private Double unitPrice;
    private Double quantity;
    private Double taxRate;

}
