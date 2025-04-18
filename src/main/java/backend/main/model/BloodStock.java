package backend.main.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodStock {
    private Integer stockId;
    private String bloodType;
    private Integer quantity;
    private String status;
    private LocalDate expirationDate;
    private String storageLocation;
}
