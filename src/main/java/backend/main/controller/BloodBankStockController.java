package backend.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.main.model.BloodBankStock;
import backend.main.repository.BloodBankStockRepository;

@RestController
@RequestMapping("/bloodBankStocks")
public class BloodBankStockController {

    private final BloodBankStockRepository bloodBankStockRepository;

    @Autowired
    public BloodBankStockController(BloodBankStockRepository bloodBankStockRepository) {
        this.bloodBankStockRepository = bloodBankStockRepository;
    }

    @GetMapping("/{bloodBankId}")
    public List<BloodBankStock> getBloodBankStocksByBloodBankId(@PathVariable Integer bloodBankId) {
        return bloodBankStockRepository.findByBloodBankId(bloodBankId);
    }

    @PostMapping
    public void addBloodBankStock(@RequestBody BloodBankStock bloodBankStock) {
        bloodBankStockRepository.save(bloodBankStock);
    }



    @DeleteMapping("/{bloodBankId}/{stockId}")
    public void deleteBloodBankStock(@PathVariable Integer bloodBankId, @PathVariable Integer stockId) {
        bloodBankStockRepository.deleteByBloodBankId(bloodBankId);
    }
}
