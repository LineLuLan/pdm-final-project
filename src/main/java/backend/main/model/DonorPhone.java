package backend.main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonorPhone {
    private Integer donorId;
    private String phone;
    private Boolean isPrimary;
}
