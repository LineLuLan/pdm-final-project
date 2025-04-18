package backend.main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientPhone {
    private Integer pid;
    private String phone;
    private Boolean isPrimary;
}
