package tr.edu.ogu.birfaturainvoiceintegration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.ogu.birfaturainvoiceintegration.model.dbmodel.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
