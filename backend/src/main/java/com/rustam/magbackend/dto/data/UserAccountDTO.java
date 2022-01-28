package com.rustam.magbackend.dto.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rustam.magbackend.dto.data.AccountDTO;
import com.rustam.magbackend.enums.PersonGender;
import com.rustam.magbackend.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserAccountDTO extends AccountDTO{
    @NotEmpty
    @Size(min = 2, max = 32)
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", flags = Pattern.Flag.UNICODE_CASE)
    private String viewNickname;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String newViewNickName;
    @NotEmpty
    @Size(max = 28)
    private String firstName;
    private String lastName;
    private Date dateBirth;
    private PersonGender gender;
    private String about;

    public UserAccountDTO() {
    }

    public UserAccountDTO(Long id, Boolean isLocked, List<RoleDTO> roles, String viewNickname) {
        super(id, isLocked, roles);
        this.viewNickname = viewNickname;
    }

    public UserAccountDTO(Long id, Boolean isLocked, String viewNickname, String firstName, String lastName, Date dateBirth) {
        super(id, isLocked);
        this.viewNickname = viewNickname;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateBirth = dateBirth;
    }

    public UserAccountDTO(Long id, Boolean isLocked, List<RoleDTO> roles, String viewNickname, String firstName, String lastName, Date dateBirth) {
        super(id, isLocked, roles);
        this.viewNickname = viewNickname;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateBirth = dateBirth;
    }

    public UserAccountDTO(Long id, Boolean isLocked, List<RoleDTO> roles, String viewNickname, String firstName, String lastName, Date dateBirth, PersonGender gender, String about) {
        super(id, isLocked, roles);
        this.viewNickname = viewNickname;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateBirth = dateBirth;
        this.gender = gender;
        this.about = about;
    }

    public UserAccountDTO(Long id, String username, String newUsername, String email, String newEmail, String password, String newPassword, Boolean isLocked, List<RoleDTO> roles, String viewNickname, String newViewNickName, String firstName, String lastName, Date dateBirth, PersonGender gender, String about) {
        super(id, username, newUsername, email, newEmail, password, newPassword, isLocked, roles);
        this.viewNickname = viewNickname;
        this.newViewNickName = newViewNickName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateBirth = dateBirth;
        this.gender = gender;
        this.about = about;
    }
}
