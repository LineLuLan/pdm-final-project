package backend.main.repository;

import backend.main.model.PatientPhones;
import java.util.List;

public interface PatientPhonesRepository {
    // JDBC repository interface for PatientPhones
    List<PatientPhones> getPatientPhonesByPatientId(Integer patientId);
    void addPatientPhone(PatientPhones patientPhones);
    void updatePatientPhone(PatientPhones patientPhones);
    void deletePatientPhone(Integer patientId, String phone);
}
