package com.rustam.magbackend.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.rustam.magbackend.dto.staff.StaffDetailsDTO;
import com.rustam.magbackend.dto.user.UserDetailsDTO;
import com.rustam.magbackend.enums.AccountType;
import lombok.*;

import java.util.List;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserDetailsDTO.class, name = "user"),
        @JsonSubTypes.Type(value = StaffDetailsDTO.class, name = "staff")
})
public abstract class AccountDetailsDTO {
    boolean isSuspended;
    String username;
    List<String> roleNames;
    AccountType type;
}
