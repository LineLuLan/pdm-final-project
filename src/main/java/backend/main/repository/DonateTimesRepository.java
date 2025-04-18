package backend.main.repository;

import java.util.List;
import java.util.Optional;

import backend.main.model.DonateTimes;

public interface DonateTimesRepository {
    Optional<DonateTimes> findById(Integer donationId);
    List<DonateTimes> findAll();
    int save(DonateTimes donation);
    int update(DonateTimes donation);
    int deleteById(Integer donationId);
    List<DonateTimes> findByDonorId(Integer donorId);
    int deleteByDonorId(Integer donorId);
}
