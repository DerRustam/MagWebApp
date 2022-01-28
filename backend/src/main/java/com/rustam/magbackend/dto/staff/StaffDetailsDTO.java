package com.rustam.magbackend.dto.staff;

import com.rustam.magbackend.dto.AccountDetailsDTO;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
public class StaffDetailsDTO extends AccountDetailsDTO {
    private String contactEmail;
    private String fullName;
}
