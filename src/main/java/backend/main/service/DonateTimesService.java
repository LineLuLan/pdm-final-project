package backend.main.service;

import backend.main.model.DonateTimes;
import backend.main.repository.DonateTimesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonateTimesService {
    private final DonateTimesRepository donateTimesRepository;

    @Autowired
    public DonateTimesService(DonateTimesRepository donateTimesRepository) {
        this.donateTimesRepository = donateTimesRepository;
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

    public void addDonateTimes(DonateTimes donation) {
        donateTimesRepository.save(donation);
    }

    public void updateDonateTimes(DonateTimes donation) {
        donateTimesRepository.update(donation);
    }

    public void deleteDonateTimes(Integer id) {
        donateTimesRepository.deleteById(id);
    }
}
