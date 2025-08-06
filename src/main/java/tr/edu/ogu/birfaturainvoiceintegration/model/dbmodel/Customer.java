package tr.edu.ogu.birfaturainvoiceintegration.model.dbmodel;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idNumber;
    private String firstName;
    private String lastName;
    private String street;
    private String buildingNo;
    private String district;
    private String city;
    private String postalCode;
}
