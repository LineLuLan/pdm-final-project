package backend.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.main.model.DonateTimes;
import backend.main.service.DonateTimesService;

@RestController
@RequestMapping("/donateTimes")
public class DonateTimesController {

    private final DonateTimesService donateTimesService;

    @Autowired
    public DonateTimesController(DonateTimesService donateTimesService) {
        this.donateTimesService = donateTimesService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonateTimes> getDonateTimesById(@PathVariable Integer id) {
        DonateTimes dt = donateTimesService.getDonateTimesById(id);
        return ResponseEntity.ok(dt);
    }

    @GetMapping
    public ResponseEntity<List<DonateTimes>> getAllDonateTimes() {
        return ResponseEntity.ok(donateTimesService.getAllDonateTimes());
    }

    @PostMapping
    public ResponseEntity<Void> addDonateTimes(@RequestBody DonateTimes donateTimes) {
        donateTimesService.addDonateTimes(donateTimes);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDonateTimes(@PathVariable Integer id, @RequestBody DonateTimes donateTimes) {
        donateTimes.setDonationId(id);
        donateTimesService.updateDonateTimes(donateTimes);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonateTimes(@PathVariable Integer id) {
        donateTimesService.deleteDonateTimes(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-donor/{donorId}")
    public ResponseEntity<List<DonateTimes>> getByDonorId(@PathVariable Integer donorId) {
        return ResponseEntity.ok(donateTimesService.getDonateTimesByDonorId(donorId));
    }
}
