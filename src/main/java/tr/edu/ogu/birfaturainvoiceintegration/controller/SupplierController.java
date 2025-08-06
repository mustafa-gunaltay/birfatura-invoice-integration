package tr.edu.ogu.birfaturainvoiceintegration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.edu.ogu.birfaturainvoiceintegration.model.dbmodel.Supplier;
import tr.edu.ogu.birfaturainvoiceintegration.repository.SupplierRepository;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierRepository supplierRepository;

    // CREATE - Yeni supplier ekle
    @PostMapping
    public ResponseEntity<Supplier> save(@RequestBody Supplier supplier) {
        return ResponseEntity.ok(supplierRepository.save(supplier));
    }

    // READ - Tüm supplier'ları getir
    @GetMapping
    public ResponseEntity<List<Supplier>> getAll() {
        return ResponseEntity.ok(supplierRepository.findAll());
    }

    // READ - Belirli bir supplier'ı ID ile getir
    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getById(@PathVariable Long id) {
        return supplierRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE - Supplier güncelle
    @PutMapping("/{id}")
    public ResponseEntity<Supplier> update(@PathVariable Long id, @RequestBody Supplier updatedSupplier) {
        return supplierRepository.findById(id)
                .map(supplier -> {
                    supplier.setTaxNumber(updatedSupplier.getTaxNumber());
                    supplier.setName(updatedSupplier.getName());
                    supplier.setEmail(updatedSupplier.getEmail());
                    supplier.setStreet(updatedSupplier.getStreet());
                    supplier.setBuildingNo(updatedSupplier.getBuildingNo());
                    supplier.setDistrict(updatedSupplier.getDistrict());
                    supplier.setCity(updatedSupplier.getCity());
                    supplier.setPostalCode(updatedSupplier.getPostalCode());
                    return ResponseEntity.ok(supplierRepository.save(supplier));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE - Supplier sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!supplierRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        supplierRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

