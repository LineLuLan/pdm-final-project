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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import backend.main.model.Donor;
import backend.main.service.DonorService;

@RestController
@RequestMapping("/donors")
public class DonorController {

    private final DonorService donorService;

    @Autowired
    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donor> getDonorById(@PathVariable Integer id) {
        Donor donor = donorService.getDonorById(id);
        return ResponseEntity.ok(donor);
    }

    @GetMapping
    public ResponseEntity<List<Donor>> getAllDonors() {
        List<Donor> donors = donorService.getAllDonors();
        return ResponseEntity.ok(donors);
    }

    // DTO for donor + phone
    static class DonorWithPhoneDTO {
        public Donor donor;
        public String phone;
    }

    @PostMapping
    public ResponseEntity<Donor> addDonor(@RequestBody DonorWithPhoneDTO donorWithPhone) {
        if (donorWithPhone.phone == null || donorWithPhone.phone.trim().isEmpty()) {
            throw new RuntimeException("Số điện thoại không được để trống!");
        }
        // Gán phone vào donor trước khi lưu
        donorWithPhone.donor.setPhone(donorWithPhone.phone);
        Donor savedDonor = donorService.addDonor(donorWithPhone.donor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDonor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Donor> updateDonor(@PathVariable Integer id, @RequestBody Donor donor) {
        Donor updatedDonor = donorService.updateDonor(id, donor);
        return ResponseEntity.ok(updatedDonor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonor(@PathVariable Integer id) {
        donorService.deleteDonor(id);
        return ResponseEntity.noContent().build();
    }
}
