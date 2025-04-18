package backend.main.service;

import backend.main.model.DonorPhone;
import backend.main.repository.DonorPhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonorPhoneService {
    private final DonorPhoneRepository donorPhoneRepository;

    @Autowired
    public DonorPhoneService(DonorPhoneRepository donorPhoneRepository) {
        this.donorPhoneRepository = donorPhoneRepository;
    }

    public List<DonorPhone> getDonorPhonesByDonorId(Integer donorId) {
        return donorPhoneRepository.findByDonorId(donorId);
    }

    public void addDonorPhone(DonorPhone donorPhone) {
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
