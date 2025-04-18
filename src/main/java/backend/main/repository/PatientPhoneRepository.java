package backend.main.repository;

import java.util.List;

import backend.main.model.PatientPhone;

public interface PatientPhoneRepository {
    List<PatientPhone> findByPatientId(Integer patientId);
    int save(PatientPhone patientPhone);
    int deleteByPatientId(Integer patientId);
}
