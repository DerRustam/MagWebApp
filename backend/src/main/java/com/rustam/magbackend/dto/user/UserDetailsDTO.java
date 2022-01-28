package com.rustam.magbackend.dto.user;

import com.rustam.magbackend.dto.AccountDetailsDTO;
import lombok.*;


@Data
@EqualsAndHashCode(callSuper = false)
public class UserDetailsDTO extends AccountDetailsDTO {
    private String viewNickname;
    private String firstName;
    private String lastName;
}
