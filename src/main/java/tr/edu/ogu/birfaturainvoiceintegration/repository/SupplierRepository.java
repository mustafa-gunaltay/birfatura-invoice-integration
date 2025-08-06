package tr.edu.ogu.birfaturainvoiceintegration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.ogu.birfaturainvoiceintegration.model.dbmodel.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
