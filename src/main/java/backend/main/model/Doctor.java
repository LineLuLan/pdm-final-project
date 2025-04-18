package backend.main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    private Integer did;
    private String name;
    private String licenseNumber;
    private String specialization;
    private String email;
    private Integer pid; // nullable foreign key
}
