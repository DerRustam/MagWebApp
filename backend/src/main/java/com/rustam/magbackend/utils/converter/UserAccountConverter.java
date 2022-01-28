package com.rustam.magbackend.utils.converter;

import com.rustam.magbackend.dto.data.RoleDTO;
import com.rustam.magbackend.dto.data.UserAccountDTO;
import com.rustam.magbackend.model.Role;
import com.rustam.magbackend.model.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

public class UserAccountConverter implements  IModelConverter<UserAccount, UserAccountDTO>{

    @Override
    public UserAccountDTO toDTO(UserAccount account) {
        List<RoleDTO> listRoles = new ArrayList<>();
        for (Role r : account.getRoles()){
            listRoles.add(new RoleDTO(r.getId(), r.getNameRole()));
        }
        return new UserAccountDTO(
                account.getId(),
                account.getLocked(),
                listRoles,
                account.getViewNickname(),
                account.getFirstName(),
                account.getLastName(),
                account.getDateBirth(),
                account.getGender(),
                account.getAbout()
        );
    }

    @Override
    public UserAccountDTO toKeyDTO(UserAccount account) {
        List<RoleDTO> listRoles = new ArrayList<>();
        for (Role r : account.getRoles()){
            listRoles.add(new RoleDTO(r.getId(), r.getNameRole()));
        }
        return new UserAccountDTO(
                account.getId(),
                account.getLocked(),
                listRoles,
                account.getViewNickname()
        );
    }
}
