package backend.main.repository;

import java.util.List;

import backend.main.model.BloodBankStock;

public interface BloodBankStockRepository {
    List<BloodBankStock> findByBloodBankId(Integer bankId);
    List<BloodBankStock> findAll();
    int save(BloodBankStock stock);
    int deleteByBloodBankId(Integer bankId);
}
