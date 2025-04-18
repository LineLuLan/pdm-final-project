package backend.main.service;

import backend.main.exception.ResourceNotFoundException;
import backend.main.model.Donor;
import backend.main.repository.DonorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonorService {
    
    private final DonorRepository donorRepository;

    public DonorService(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    public Donor getDonorById(Integer donorId) {
        return donorRepository.findById(donorId)
                .orElseThrow(() -> new ResourceNotFoundException("Donor not found with id: " + donorId));
    }

    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    public Donor addDonor(Donor donor) {
        donorRepository.save(donor);
        return donor;
    }

    public Donor updateDonor(Integer donorId, Donor donorDetails) {
        Donor donor = getDonorById(donorId);
        donor.setName(donorDetails.getName());
        // Cập nhật các trường khác tương tự
        donorRepository.update(donor);
        return donor;
    }

    public void deleteDonor(Integer donorId) {
        Donor donor = getDonorById(donorId);
        donorRepository.deleteById(donor.getDonorId());
    }
}