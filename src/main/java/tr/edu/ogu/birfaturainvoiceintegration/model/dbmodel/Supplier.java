package tr.edu.ogu.birfaturainvoiceintegration.model.dbmodel;


import jakarta.persistence.*;

@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taxNumber;
    private String name;
    private String email;
    private String street;
    private String buildingNo;
    private String district;
    private String city;
    private String postalCode;
}
