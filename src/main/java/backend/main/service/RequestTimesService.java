package backend.main.service;

import backend.main.model.RequestTimes;
import backend.main.repository.RequestTimesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestTimesService {

    private final RequestTimesRepository requestTimesRepository;

    @Autowired
    public RequestTimesService(RequestTimesRepository requestTimesRepository) {
        this.requestTimesRepository = requestTimesRepository;
    }

    public List<RequestTimes> getRequestTimesByPatientId(Integer patientId) {
        return requestTimesRepository.findByPatientId(patientId);
    }

    public int addRequestTimes(RequestTimes requestTimes) {
        return requestTimesRepository.save(requestTimes);
    }

    public int deleteRequestTimesByPatientId(Integer patientId) {
        return requestTimesRepository.deleteByPatientId(patientId);
    }
}
