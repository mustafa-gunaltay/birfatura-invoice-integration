package tr.edu.ogu.birfaturainvoiceintegration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.edu.ogu.birfaturainvoiceintegration.model.dbmodel.Invoice;
import tr.edu.ogu.birfaturainvoiceintegration.repository.InvoiceRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceRepository invoiceRepository;

    // CREATE - Yeni fatura oluştur
    @PostMapping
    public ResponseEntity<Invoice> save(@RequestBody Invoice invoice) {
        if (invoice.getUuid() == null) {
            invoice.setUuid(UUID.randomUUID());
        }
        return ResponseEntity.ok(invoiceRepository.save(invoice));
    }

    // READ - Tüm faturaları getir
    @GetMapping
    public ResponseEntity<List<Invoice>> getAll() {
        return ResponseEntity.ok(invoiceRepository.findAll());
    }

    // READ - Belirli bir faturayı getir
    @GetMapping("/{uuid}")
    public ResponseEntity<Invoice> getById(@PathVariable UUID uuid) {
        return invoiceRepository.findById(uuid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE - Faturayı güncelle
    @PutMapping("/{uuid}")
    public ResponseEntity<Invoice> update(@PathVariable UUID uuid, @RequestBody Invoice updatedInvoice) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(uuid);
        if (optionalInvoice.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Invoice invoice = optionalInvoice.get();
        invoice.setInvoiceDate(updatedInvoice.getInvoiceDate());
        invoice.setInvoiceTime(updatedInvoice.getInvoiceTime());
        invoice.setPdfUrl(updatedInvoice.getPdfUrl());
        invoice.setNote(updatedInvoice.getNote());
        invoice.setCustomer(updatedInvoice.getCustomer());
        invoice.setSupplier(updatedInvoice.getSupplier());
        invoice.setInvoiceItem(updatedInvoice.getInvoiceItem());

        return ResponseEntity.ok(invoiceRepository.save(invoice));
    }

    // DELETE - Faturayı sil
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        if (!invoiceRepository.existsById(uuid)) {
            return ResponseEntity.notFound().build();
        }
        invoiceRepository.deleteById(uuid);
        return ResponseEntity.noContent().build();
    }
}

