package com.uade.beappsint.dto.auth;

import com.uade.beappsint.enums.KycStatusEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CustomerInfoDTO {
    private Integer id;

    // Basic Info
    private String email;
    private String  firstname;
    private String  lastname;
    private LocalDate dateOfBirth;
    private Boolean isAdmin;
    private KycStatusEnum kycStatus;

    // Residential Info
    private String streetName;
    private String streetNumber;
    private String complementaryAddress; // Floor, department number, if applicable
    private String phoneNumber;
}
