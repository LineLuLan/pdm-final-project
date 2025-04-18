package backend.main.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    private Integer pid;
    private String name;
    private String bloodType;
    private Integer age;
    private String gender;
    private String medicalHistory;
    private LocalDateTime registrationDate;
    private LocalDateTime lastUpdated;
}
