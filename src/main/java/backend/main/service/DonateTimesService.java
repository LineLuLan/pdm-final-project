package backend.main.service;

import backend.main.model.DonateTimes;
import backend.main.repository.DonateTimesRepository;
import backend.main.repository.BloodStockRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonateTimesService {
    private final DonateTimesRepository donateTimesRepository;
    private final BloodStockRepository bloodStockRepository;

    @Autowired
    public DonateTimesService(DonateTimesRepository donateTimesRepository, BloodStockRepository bloodStockRepository) {
        this.donateTimesRepository = donateTimesRepository;
        this.bloodStockRepository = bloodStockRepository;
    }

    public List<DonateTimes> getDonateTimesByDonorId(Integer donorId) {
        return donateTimesRepository.findByDonorId(donorId);
    }

    public DonateTimes getDonateTimesById(Integer id) {
        return donateTimesRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Donation not found with id: " + id));
    }

    public List<DonateTimes> getAllDonateTimes() {
        return donateTimesRepository.findAll();
    }

    @Transactional
    public void addDonateTimes(DonateTimes donation) {
        if (donation.getDonationDate() == null) {
            donation.setDonationDate(java.time.LocalDateTime.now());
        }
        donateTimesRepository.save(donation);
        // Cập nhật số lượng máu trong kho (BloodStock)
        bloodStockRepository.incrementQuantityByBid(donation.getBid(), donation.getQuantity());
    }

    public void updateDonateTimes(DonateTimes donation) {
        donateTimesRepository.update(donation);
    }

    public void deleteDonateTimes(Integer id) {
        donateTimesRepository.deleteById(id);
    }
}
