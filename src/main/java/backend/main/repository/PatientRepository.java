package backend.main.repository;

import java.util.List;
import java.util.Optional;

import backend.main.model.Patient;

public interface PatientRepository {
    Optional<Patient> findById(Integer patientId);
    List<Patient> findAll();
    int save(Patient patient);
    int update(Patient patient);
    int deleteById(Integer patientId);
    List<Patient> findByBloodType(String bloodType);
    List<Patient> findUrgentCases();
    Optional<Patient> findByPhone(String phone);
    List<Patient> findByIdentity(String name, String bloodType, Integer age, String gender);
}
