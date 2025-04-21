package backend.main.repository;

import java.util.List;

import backend.main.model.PatientPhone;

import org.springframework.stereotype.Repository;

@Repository
public interface PatientPhoneRepository {
    boolean existsByPhoneAndName(String phone, String name);
    List<backend.main.model.PatientPhone> getAllPatientPhones();
    List<PatientPhone> findByPatientId(Integer patientId);
    int save(PatientPhone patientPhone);
    int deleteByPatientId(Integer patientId);
}
