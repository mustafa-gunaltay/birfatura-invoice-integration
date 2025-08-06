package tr.edu.ogu.birfaturainvoiceintegration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.edu.ogu.birfaturainvoiceintegration.model.dbmodel.InvoiceItem;
import tr.edu.ogu.birfaturainvoiceintegration.repository.InvoiceItemRepository;

import java.util.List;

@RestController
@RequestMapping("/api/invoice-items")
@RequiredArgsConstructor
public class InvoiceItemController {

    private final InvoiceItemRepository invoiceItemRepository;

    // CREATE - Yeni ürün ekle
    @PostMapping
    public ResponseEntity<InvoiceItem> save(@RequestBody InvoiceItem invoiceItem) {
        return ResponseEntity.ok(invoiceItemRepository.save(invoiceItem));
    }

    // READ - Tüm ürünleri getir
    @GetMapping
    public ResponseEntity<List<InvoiceItem>> getAll() {
        return ResponseEntity.ok(invoiceItemRepository.findAll());
    }

    // READ - Belirli bir ürünü ID ile getir
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceItem> getById(@PathVariable Long id) {
        return invoiceItemRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE - Ürünü güncelle
    @PutMapping("/{id}")
    public ResponseEntity<InvoiceItem> update(@PathVariable Long id, @RequestBody InvoiceItem updatedItem) {
        return invoiceItemRepository.findById(id)
                .map(item -> {
                    item.setProductName(updatedItem.getProductName());
                    item.setUnitPrice(updatedItem.getUnitPrice());
                    item.setQuantity(updatedItem.getQuantity());
                    item.setTaxRate(updatedItem.getTaxRate());
                    return ResponseEntity.ok(invoiceItemRepository.save(item));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE - Ürünü sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!invoiceItemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        invoiceItemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

