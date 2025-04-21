package backend.main.service;

import backend.main.model.Doctor;
import backend.main.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor getDoctorById(Integer id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public int addDoctor(Doctor doctor) {
        // Kiểm tra trùng licenseNumber
        if (doctor.getLicenseNumber() != null && !doctor.getLicenseNumber().trim().isEmpty()) {
            if (doctorRepository.findByLicenseNumber(doctor.getLicenseNumber()).isPresent()) {
                throw new RuntimeException("Đã tồn tại bác sĩ với licenseNumber này!");
            }
        }
        // Kiểm tra trùng email
        if (doctor.getEmail() != null && !doctor.getEmail().trim().isEmpty()) {
            if (doctorRepository.findByEmail(doctor.getEmail()).isPresent()) {
                throw new RuntimeException("Đã tồn tại bác sĩ với email này!");
            }
        }
        return doctorRepository.save(doctor);
    }

    public int updateDoctor(Doctor doctor) {
        return doctorRepository.update(doctor);
    }

    public int deleteDoctor(Integer id) {
        return doctorRepository.deleteById(id);
    }
}
