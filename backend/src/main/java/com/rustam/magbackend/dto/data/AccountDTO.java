package com.rustam.magbackend.dto.data;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserAccountDTO.class, name = "user"),
        @JsonSubTypes.Type(value = StaffAccountDTO.class, name = "staff")
})
public abstract class AccountDTO {

    private Long id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String newUsername;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String newEmail;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String newPassword;

    @NotNull
    private Boolean isLocked;
    private List<RoleDTO> roles;

    public AccountDTO() {
    }

    public AccountDTO(Long id, Boolean isLocked) {
        this.id = id;
        this.isLocked = isLocked;
    }

    public AccountDTO(Long id, Boolean isLocked, List<RoleDTO> roles) {
        this.id = id;
        this.isLocked = isLocked;
        this.roles = roles;
    }

    public AccountDTO(Long id, String username, String newUsername, String email, String newEmail, String password, String newPassword, Boolean isLocked, List<RoleDTO> roles) {
        this.id = id;
        this.username = username;
        this.newUsername = newUsername;
        this.email = email;
        this.newEmail = newEmail;
        this.password = password;
        this.newPassword = newPassword;
        this.isLocked = isLocked;
        this.roles = roles;
    }
}
