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

import backend.main.model.PatientPhones;
import backend.main.repository.PatientPhonesRepository;

@RestController
@RequestMapping("/patientPhones")
public class PatientPhonesController {

    private final PatientPhonesRepository patientPhonesRepository;

    @Autowired
    public PatientPhonesController(PatientPhonesRepository patientPhonesRepository) {
        this.patientPhonesRepository = patientPhonesRepository;
    }

    @GetMapping("/{patientId}")
    public List<PatientPhones> getPatientPhonesByPatientId(@PathVariable Integer patientId) {
        return patientPhonesRepository.getPatientPhonesByPatientId(patientId);
    }

    @PostMapping
    public void addPatientPhone(@RequestBody PatientPhones patientPhones) {
        patientPhonesRepository.addPatientPhone(patientPhones);
    }

    @PutMapping
    public void updatePatientPhone(@RequestBody PatientPhones patientPhones) {
        patientPhonesRepository.updatePatientPhone(patientPhones);
    }

    @DeleteMapping("/{patientId}/{phone}")
    public void deletePatientPhone(@PathVariable Integer patientId, @PathVariable String phone) {
        patientPhonesRepository.deletePatientPhone(patientId, phone);
    }
}
