package backend.main.service;

import backend.main.model.RequestTimes;
import backend.main.repository.RequestTimesRepository;
import backend.main.repository.BloodStockRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestTimesService {

    private final RequestTimesRepository requestTimesRepository;
    private final BloodStockRepository bloodStockRepository;

    @Autowired
    public RequestTimesService(RequestTimesRepository requestTimesRepository, BloodStockRepository bloodStockRepository) {
        this.requestTimesRepository = requestTimesRepository;
        this.bloodStockRepository = bloodStockRepository;
    }

    public List<RequestTimes> getRequestTimesByPatientId(Integer patientId) {
        return requestTimesRepository.findByPatientId(patientId);
    }

    public List<RequestTimes> getAllRequestTimes() {
        return requestTimesRepository.findAll();
    }

    @Transactional
public int addRequestTimes(RequestTimes requestTimes) {
    // Check if enough blood stock exists
    var stockOpt = bloodStockRepository.findById(requestTimes.getBid());
    if (stockOpt.isEmpty()) {
        // Stock not found
        return -2; // Custom code for stock not found
    }
    var stock = stockOpt.get();
    if (stock.getQuantity() < requestTimes.getQuantity()) {
        // Not enough stock
        return -1; // Custom code for insufficient stock
    }
    int result = requestTimesRepository.save(requestTimes);
    if (result > 0) {
        // Only decrement if save successful
        bloodStockRepository.decrementQuantityByBid(requestTimes.getBid(), requestTimes.getQuantity());
    }
    return result;
}

    public int deleteRequestTimesByPatientId(Integer patientId) {
        return requestTimesRepository.deleteByPatientId(patientId);
    }
}
