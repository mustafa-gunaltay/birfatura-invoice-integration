package tr.edu.ogu.birfaturainvoiceintegration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.ogu.birfaturainvoiceintegration.model.dbmodel.Invoice;

import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
}
