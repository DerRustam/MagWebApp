package com.rustam.magbackend.utils.converter;

import com.rustam.magbackend.dto.data.PrivilegeDTO;
import com.rustam.magbackend.dto.data.RoleDTO;
import com.rustam.magbackend.model.Privilege;
import com.rustam.magbackend.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

public class RoleConverter implements IModelConverter<Role, RoleDTO>{
    @Override
    public RoleDTO toDTO(Role role) {
        List<PrivilegeDTO> privileges = new ArrayList<>();
        for (Privilege p: role.getPrivileges()){
            privileges.add(new PrivilegeDTO(p.getId(), p.getNamePrivilege()));
        }
        return new RoleDTO(role.getId(), role.getNameRole(), role.getDescriptionRole(), privileges);
    }

    @Override
    public RoleDTO toKeyDTO(Role role) {
        return new RoleDTO(role.getId(), role.getNameRole(), role.getDescriptionRole());
    }
}
