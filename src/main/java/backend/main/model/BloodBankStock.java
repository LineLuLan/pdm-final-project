package backend.main.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodBankStock {
    private Integer bid;
    private Integer stockId;
    private LocalDateTime assignmentDate;
}
