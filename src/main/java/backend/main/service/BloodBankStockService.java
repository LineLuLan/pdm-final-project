package backend.main.service;

import backend.main.model.BloodBankStock;
import backend.main.repository.BloodBankStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BloodBankStockService {
    private final BloodBankStockRepository bloodBankStockRepository;

    @Autowired
    public BloodBankStockService(BloodBankStockRepository bloodBankStockRepository) {
        this.bloodBankStockRepository = bloodBankStockRepository;
    }

    public List<BloodBankStock> getStocksByBloodBankId(Integer bankId) {
        return bloodBankStockRepository.findByBloodBankId(bankId);
    }

    public int addBloodBankStock(BloodBankStock stock) {
        return bloodBankStockRepository.save(stock);
    }

    public int deleteBloodBankStock(Integer bankId) {
        return bloodBankStockRepository.deleteByBloodBankId(bankId);
    }
}
