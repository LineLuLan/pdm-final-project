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

import backend.main.model.PatientPhone;
import backend.main.repository.PatientPhoneRepository;

@RestController
@RequestMapping("/patientPhones")
public class PatientPhonesController {

    @GetMapping
    public List<PatientPhone> getAllPatientPhones() {
        return patientPhoneRepository.getAllPatientPhones();
    }

    private final PatientPhoneRepository patientPhoneRepository;

    @Autowired
    public PatientPhonesController(PatientPhoneRepository patientPhoneRepository) {
        this.patientPhoneRepository = patientPhoneRepository;
    }

    @GetMapping("/{patientId}")
    public List<PatientPhone> getPatientPhonesByPatientId(@PathVariable Integer patientId) {
        return patientPhoneRepository.findByPatientId(patientId);
    }

    @PostMapping
    public void addPatientPhone(@RequestBody PatientPhone patientPhone) {
        patientPhoneRepository.save(patientPhone);
    }

    @PutMapping
    // No update method implemented for PatientPhone

    @DeleteMapping("/{patientId}/{phone}")
    public void deletePatientPhone(@PathVariable Integer patientId, @PathVariable String phone) {
        // Implement if needed
    }
}
