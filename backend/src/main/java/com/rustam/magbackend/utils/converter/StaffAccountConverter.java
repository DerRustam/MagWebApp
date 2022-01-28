package com.rustam.magbackend.utils.converter;

import com.rustam.magbackend.dto.data.RoleDTO;
import com.rustam.magbackend.dto.data.StaffAccountDTO;
import com.rustam.magbackend.model.Role;
import com.rustam.magbackend.model.StaffAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

public class StaffAccountConverter implements IModelConverter<StaffAccount, StaffAccountDTO>{

    @Override
    public StaffAccountDTO toDTO(StaffAccount account) {
        List<RoleDTO> listRoles = new ArrayList<>();
        for (Role r : account.getRoles()){
            listRoles.add(new RoleDTO(r.getId(), r.getNameRole()));
        }
        return new StaffAccountDTO(
                account.getId(),
                account.getLocked(),
                listRoles,
                account.getStaffMemberUid(),
                account.getContactEmail(),
                account.getContactPhone(),
                account.getFullName(),
                account.getDateHire(),
                account.getDateBirth(),
                account.getGender()
        );
    }

    @Override
    public StaffAccountDTO toKeyDTO(StaffAccount account) {
        List<RoleDTO> listRoles = new ArrayList<>();
        for (Role r : account.getRoles()){
            listRoles.add(new RoleDTO(r.getId(), r.getNameRole()));
        }
        return new StaffAccountDTO(
                account.getId(),
                account.getLocked(),
                listRoles,
                account.getStaffMemberUid(),
                account.getContactEmail(),
                account.getFullName()
        );
    }
}
