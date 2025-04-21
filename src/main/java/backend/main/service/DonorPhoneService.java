package backend.main.service;

import backend.main.model.DonorPhone;
import backend.main.repository.DonorPhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonorPhoneService {
    public List<DonorPhone> getAllDonorPhones() {
        return donorPhoneRepository.getAllDonorPhones();
    }
    private final DonorPhoneRepository donorPhoneRepository;

    @Autowired
    public DonorPhoneService(DonorPhoneRepository donorPhoneRepository) {
        this.donorPhoneRepository = donorPhoneRepository;
    }

    public List<DonorPhone> getDonorPhonesByDonorId(Integer donorId) {
        return donorPhoneRepository.findByDonorId(donorId);
    }

    public void addDonorPhone(DonorPhone donorPhone, String donorName) {
    if (donorPhone.getPhone() == null || donorPhone.getPhone().trim().isEmpty()) {
        throw new RuntimeException("Số điện thoại không được để trống!");
    }
    // Nếu vừa trùng tên vừa trùng phone thì không cho phép
    if (donorPhoneRepository.existsByPhoneAndName(donorPhone.getPhone(), donorName)) {
        throw new RuntimeException("Đã tồn tại donor với cùng tên và số điện thoại này!");
    }

        // Check duplicate: donorId + phone
        List<DonorPhone> existing = donorPhoneRepository.findByDonorId(donorPhone.getDonorId());
        boolean duplicate = existing.stream().anyMatch(dp -> dp.getPhone().equals(donorPhone.getPhone()));
        if (duplicate) {
            throw new RuntimeException("Donor phone already exists for this donor!");
        }
        donorPhoneRepository.save(donorPhone);
    }

    // No update method in repository, so use save for upsert
    public void updateDonorPhone(DonorPhone donorPhone) {
        donorPhoneRepository.save(donorPhone);
    }

    // No delete by phone, only by donorId
    public void deleteDonorPhone(Integer donorId, String phone) {
        donorPhoneRepository.deleteByDonorId(donorId);
    }
}
