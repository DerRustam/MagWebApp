package com.rustam.magbackend.dto.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rustam.magbackend.dto.data.AccountDTO;
import com.rustam.magbackend.enums.PersonGender;
import com.rustam.magbackend.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.sql.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class StaffAccountDTO extends AccountDTO{
    @NotEmpty
    @Size(min = 6, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", flags = Pattern.Flag.UNICODE_CASE)
    private String staffMemberUid;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String newStaffMemberUid;
    private String contactPhone;

    @NotEmpty
    @Email
    private String contactEmail;
    @NotEmpty
    @Size(min = 3, max = 100)
    private String fullName;
    @NotNull
    private Date dateHire;
    @NotNull
    private Date dateBirth;
    @NotNull
    private PersonGender gender;

    public StaffAccountDTO() {
    }

    public StaffAccountDTO(Long id, Boolean isLocked, String staffMemberUid, String contactEmail, String fullName) {
        super(id, isLocked);
        this.staffMemberUid = staffMemberUid;
        this.contactEmail = contactEmail;
        this.fullName = fullName;
    }

    public StaffAccountDTO(Long id, Boolean isLocked, List<RoleDTO> roles, String staffMemberUid, String contactEmail, String fullName) {
        super(id, isLocked, roles);
        this.staffMemberUid = staffMemberUid;
        this.contactEmail = contactEmail;
        this.fullName = fullName;
    }

    public StaffAccountDTO(Long id, Boolean isLocked, List<RoleDTO> roles, String staffMemberUid, String contactEmail, String contactPhone, String fullName, Date dateHire, Date dateBirth, PersonGender gender) {
        super(id, isLocked, roles);
        this.staffMemberUid = staffMemberUid;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.fullName = fullName;
        this.dateHire = dateHire;
        this.dateBirth = dateBirth;
        this.gender = gender;
    }

    public StaffAccountDTO(Long id, String username, String newUsername, String email, String newEmail, String password, String newPassword, Boolean isLocked, List<RoleDTO> roles, String staffMemberUid, String newStaffMemberUid, String contactPhone, String contactEmail, String fullName, Date dateHire, Date dateBirth, PersonGender gender) {
        super(id, username, newUsername, email, newEmail, password, newPassword, isLocked, roles);
        this.staffMemberUid = staffMemberUid;
        this.newStaffMemberUid = newStaffMemberUid;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.fullName = fullName;
        this.dateHire = dateHire;
        this.dateBirth = dateBirth;
        this.gender = gender;
    }
}
