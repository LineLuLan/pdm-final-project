package backend.main.service;

import backend.main.model.Patient;
import backend.main.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient getPatientById(Integer id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient addPatient(Patient patient) {
        int result = patientRepository.save(patient);
        if (result > 0 && patient.getPid() != null) {
            return getPatientById(patient.getPid());
        } else {
            // Try to fetch by some unique field if pid not set, or throw
            throw new RuntimeException("Failed to save patient or PID not assigned.");
        }
    }

    public Patient updatePatient(Integer id, Patient patient) {
        patient.setPid(id);
        int result = patientRepository.update(patient);
        if (result > 0) {
            return getPatientById(id);
        } else {
            throw new RuntimeException("Failed to update patient with id: " + id);
        }
    }

    public void deletePatient(Integer id) {
        int result = patientRepository.deleteById(id);
        if (result == 0) {
            throw new RuntimeException("Failed to delete patient with id: " + id);
        }
    }
}
