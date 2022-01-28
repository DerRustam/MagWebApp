package com.rustam.magbackend.utils.converter;

import com.rustam.magbackend.dto.data.PrivilegeDTO;
import com.rustam.magbackend.model.Privilege;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

public class PrivilegeConverter implements IModelConverter<Privilege, PrivilegeDTO>{
    @Override
    public PrivilegeDTO toDTO(Privilege privilege) {
        return new PrivilegeDTO(privilege.getId(), privilege.getNamePrivilege(), privilege.getDescriptionPrivilege());
    }

    @Override
    public PrivilegeDTO toKeyDTO(Privilege privilege) {
        return new PrivilegeDTO(privilege.getId(), privilege.getNamePrivilege());
    }
}
