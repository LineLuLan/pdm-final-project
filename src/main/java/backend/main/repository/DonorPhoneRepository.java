package backend.main.repository;

import java.util.List;

import backend.main.model.DonorPhone;

public interface DonorPhoneRepository {
    boolean existsByPhoneAndName(String phone, String name);
    List<DonorPhone> getAllDonorPhones();
    // JDBC repository interface for DonorPhone
    List<DonorPhone> findByDonorId(Integer donorId);
    int save(DonorPhone donorPhone);
    int deleteByDonorId(Integer donorId);
}
