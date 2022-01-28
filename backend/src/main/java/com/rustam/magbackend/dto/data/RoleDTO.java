package com.rustam.magbackend.dto.data;

import com.rustam.magbackend.dto.data.PrivilegeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class RoleDTO {
    private Short id;
    @Size(min = 1, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", flags = Pattern.Flag.UNICODE_CASE)
    private String nameRole;
    private String descriptionRole;
    private List<PrivilegeDTO> privileges;

    public RoleDTO() {
    }

    public RoleDTO(Short id, String nameRole) {
        this.id = id;
        this.nameRole = nameRole;
    }

    public RoleDTO(Short id, String nameRole, String descriptionRole) {
        this.id = id;
        this.nameRole = nameRole;
        this.descriptionRole = descriptionRole;
    }

    public RoleDTO(Short id, String nameRole, String descriptionRole, List<PrivilegeDTO> privileges) {
        this.id = id;
        this.nameRole = nameRole;
        this.descriptionRole = descriptionRole;
        this.privileges = privileges;
    }
}
