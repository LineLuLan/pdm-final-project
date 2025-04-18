package backend.main.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Donor {
    private Integer donorId;
    private String name;
    private String gender;
    private Integer age;
    private String bloodType;
    private Double weight;
    private LocalDate lastDonationDate;
    private String healthStatus;
    private Boolean isEligible;
    private LocalDateTime registrationDate;
}