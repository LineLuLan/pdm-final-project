package backend.main.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonateTimes {
    private Integer donationId;
    private Integer bid;
    private Integer donorId;
    private LocalDateTime donationDate;
    private Integer quantity;
    private String notes;
}
