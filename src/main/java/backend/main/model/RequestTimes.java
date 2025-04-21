package backend.main.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestTimes {
    // For repository compatibility

    // Existing fields
    private Integer requestId;
    private Integer did;
    private Integer bid;
    private Integer patientId;
    private LocalDateTime requestDate;
    private String bloodType;
    private Integer quantity;
    private String urgency;
    private String status;
}
