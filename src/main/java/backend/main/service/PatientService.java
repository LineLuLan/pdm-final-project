package backend.main.service;

import backend.main.model.Patient;
import backend.main.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final backend.main.repository.PatientPhoneRepository patientPhoneRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository, backend.main.repository.PatientPhoneRepository patientPhoneRepository) {
        this.patientRepository = patientRepository;
        this.patientPhoneRepository = patientPhoneRepository;
    }

    public Patient getPatientById(Integer id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient addPatient(Patient patient, String phone) {
    if (phone == null || phone.trim().isEmpty()) {
        throw new RuntimeException("Số điện thoại không được để trống!");
    }
    // Nếu vừa trùng tên vừa trùng phone thì không cho phép
    if (patientPhoneRepository.existsByPhoneAndName(phone, patient.getName())) {
        throw new RuntimeException("Đã tồn tại bệnh nhân với cùng tên và số điện thoại này!");
    }
        // Kiểm tra trùng hoàn toàn: tất cả các trường identity + phone
        List<Patient> candidates = patientRepository.findByIdentity(patient.getName(), patient.getBloodType(), patient.getAge(), patient.getGender());
        for (Patient p : candidates) {
            // Bạn cần PatientPhoneRepository để lấy số điện thoại
            // Giả sử đã có patientPhoneRepository (bạn cần inject nó vào Service)
            List<backend.main.model.PatientPhone> phones = patientPhoneRepository.findByPatientId(p.getPid());
            for (backend.main.model.PatientPhone pp : phones) {
                if (pp.getPhone().equals(phone)) {
                    throw new RuntimeException("Bệnh nhân đã tồn tại với thông tin và số điện thoại này!");
                }
            }
        }
        int result = patientRepository.save(patient);
        if (result > 0 && patient.getPid() != null) {
            // Sau khi lưu mới, thêm phone cho bệnh nhân
            backend.main.model.PatientPhone patientPhone = new backend.main.model.PatientPhone(patient.getPid(), phone, true);
            int phoneResult = patientPhoneRepository.save(patientPhone);
            if (phoneResult > 0) {
                return getPatientById(patient.getPid());
            } else {
                // Rollback: xóa bệnh nhân vừa tạo nếu lưu phone thất bại
                patientRepository.deleteById(patient.getPid());
                throw new RuntimeException("Failed to save patient phone, patient creation rolled back!");
            }
        } else {
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
