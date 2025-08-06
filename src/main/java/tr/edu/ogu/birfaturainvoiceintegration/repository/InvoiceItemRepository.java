package tr.edu.ogu.birfaturainvoiceintegration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.ogu.birfaturainvoiceintegration.model.dbmodel.InvoiceItem;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
}
