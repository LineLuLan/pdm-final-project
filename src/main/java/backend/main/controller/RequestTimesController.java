package backend.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.main.model.RequestTimes;
import backend.main.service.RequestTimesService;

@RestController
@RequestMapping("/requestTimes")
public class RequestTimesController {

    @GetMapping
    public ResponseEntity<List<RequestTimes>> getAllRequestTimes() {
        return ResponseEntity.ok(requestTimesService.getAllRequestTimes());
    }

    private final RequestTimesService requestTimesService;

    @Autowired
    public RequestTimesController(RequestTimesService requestTimesService) {
        this.requestTimesService = requestTimesService;
    }

    // Get all request times for a patient
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<RequestTimes>> getRequestTimesByPatientId(@PathVariable Integer patientId) {
        List<RequestTimes> list = requestTimesService.getRequestTimesByPatientId(patientId);
        return ResponseEntity.ok(list);
    }

    // Add a new request time
    @PostMapping
    public ResponseEntity<String> addRequestTimes(@RequestBody RequestTimes requestTimes) {
        int result = requestTimesService.addRequestTimes(requestTimes);
        if (result > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Request created successfully.");
        } else if (result == -1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient blood stock to fulfill the request.");
        } else if (result == -2) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blood stock not found for the given ID.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create request due to server error.");
        }
    }

    // Delete all request times for a patient
    @DeleteMapping("/patient/{patientId}")
    public ResponseEntity<Void> deleteRequestTimesByPatientId(@PathVariable Integer patientId) {
        int result = requestTimesService.deleteRequestTimesByPatientId(patientId);
        if (result > 0) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
