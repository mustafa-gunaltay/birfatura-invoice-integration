package tr.edu.ogu.birfaturainvoiceintegration.model.dbmodel;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;


@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    private UUID uuid;

    private LocalDate invoiceDate;
    private LocalTime invoiceTime;
    private String pdfUrl;

    private String note;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_item_id")
    private InvoiceItem invoiceItem;
}
