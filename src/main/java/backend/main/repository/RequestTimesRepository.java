package backend.main.repository;

import backend.main.model.RequestTimes;
import java.util.List;

public interface RequestTimesRepository {
    List<RequestTimes> findAll();
    List<RequestTimes> findByPatientId(Integer patientId);
    int save(RequestTimes request);
    int deleteByPatientId(Integer patientId);
}
