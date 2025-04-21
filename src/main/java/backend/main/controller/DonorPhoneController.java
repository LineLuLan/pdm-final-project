package backend.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.main.model.DonorPhone;
import backend.main.service.DonorPhoneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/donorPhones")
public class DonorPhoneController {
    private final DonorPhoneService donorPhoneService;
    private final backend.main.service.DonorService donorService;

    @Autowired
    public DonorPhoneController(DonorPhoneService donorPhoneService, backend.main.service.DonorService donorService) {
        this.donorPhoneService = donorPhoneService;
        this.donorService = donorService;
    }

    @GetMapping
    public ResponseEntity<List<DonorPhone>> getAllDonorPhones() {
        return ResponseEntity.ok(donorPhoneService.getAllDonorPhones());
    }


    @GetMapping("/{donorId}")
    public ResponseEntity<List<DonorPhone>> getDonorPhonesByDonorId(@PathVariable Integer donorId) {
        return ResponseEntity.ok(donorPhoneService.getDonorPhonesByDonorId(donorId));
    }

    @PostMapping
    public ResponseEntity<Void> addDonorPhone(@RequestBody DonorPhone donorPhone) {
        // Giả sử bạn có thể lấy tên donor từ một service khác dựa vào donorId
        String donorName = "";
        // TODO: Lấy tên donor từ donorId
        // Ví dụ: donorService.getDonorById(donorPhone.getDonorId()).getName();
        try {
            donorName = donorService.getDonorById(donorPhone.getDonorId()).getName();
        } catch (Exception e) {
            throw new RuntimeException("Không tìm thấy donor với donorId: " + donorPhone.getDonorId());
        }
        donorPhoneService.addDonorPhone(donorPhone, donorName);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateDonorPhone(@RequestBody DonorPhone donorPhone) {
        donorPhoneService.updateDonorPhone(donorPhone);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{donorId}/{phone}")
    public ResponseEntity<Void> deleteDonorPhone(@PathVariable Integer donorId, @PathVariable String phone) {
        donorPhoneService.deleteDonorPhone(donorId, phone);
        return ResponseEntity.noContent().build();
    }
}
