package com.rustam.magbackend.dto.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class PrivilegeDTO {
    private Short id;
    @Size(min = 1, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", flags = Pattern.Flag.UNICODE_CASE)
    private String namePrivilege;
    private String descriptionPrivilege;

    public PrivilegeDTO() {
    }

    public PrivilegeDTO(Short id, String namePrivilege) {
        this.id = id;
        this.namePrivilege = namePrivilege;
    }

    public PrivilegeDTO(Short id, String namePrivilege, String descriptionPrivilege) {
        this.id = id;
        this.namePrivilege = namePrivilege;
        this.descriptionPrivilege = descriptionPrivilege;
    }
}
