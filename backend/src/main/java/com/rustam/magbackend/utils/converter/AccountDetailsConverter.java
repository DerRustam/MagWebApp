package com.rustam.magbackend.utils.converter;

import com.rustam.magbackend.dto.staff.StaffDetailsDTO;
import com.rustam.magbackend.dto.user.UserDetailsDTO;
import com.rustam.magbackend.enums.AccountType;
import com.rustam.magbackend.model.Role;
import com.rustam.magbackend.model.StaffAccount;
import com.rustam.magbackend.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AccountDetailsConverter {

    Environment env;

    private Map<String, String> roleNamesMap;

    @Autowired
    public AccountDetailsConverter(Environment environment) {
        this.env = environment;
        roleNamesMap = new HashMap<String, String>();
        roleNamesMap.put(env.getProperty("roles.USER_WATCH_SAFE"), "USER_WATCH_SAFE");
        roleNamesMap.put(env.getProperty("roles.USER_WATCH_ALL"), "USER_WATCH_ALL");
        roleNamesMap.put(env.getProperty("roles.USER_PUBLISH_SAFE"), "USER_PUBLISH_SAFE");
        roleNamesMap.put(env.getProperty("roles.USER_PUBLISH_ALL"), "USER_PUBLISH_ALL");
        roleNamesMap.put(env.getProperty("roles.STAFF_MODERATOR"), "STAFF_MODERATOR");
        roleNamesMap.put(env.getProperty("roles.STAFF_ADMIN"), "STAFF_ADMIN");
        roleNamesMap.put(env.getProperty("roles.STAFF_HEAD_ADMIN"), "STAFF_HEAD_ADMIN");
    }

    public UserDetailsDTO toDTO(UserAccount ua){
        UserDetailsDTO detailsDTO = new UserDetailsDTO();
        List<String> roleNames = new ArrayList<>();
        for (Role role : ua.getRoles()) {
            roleNames.add(roleNamesMap.get(role.getNameRole()));
        }
        detailsDTO.setUsername(ua.getUsername());
        detailsDTO.setType(AccountType.USER);
        detailsDTO.setSuspended(ua.getLocked());
        detailsDTO.setRoleNames(roleNames);
        detailsDTO.setViewNickname(ua.getViewNickname());
        detailsDTO.setFirstName(ua.getFirstName());
        detailsDTO.setLastName(ua.getLastName());
        return detailsDTO;
    }

    public StaffDetailsDTO toDTO(StaffAccount sa){
        StaffDetailsDTO detailsDTO = new StaffDetailsDTO();
        List<String> roleNames = new ArrayList<>();
        for (Role role : sa.getRoles()) {
            roleNames.add(roleNamesMap.get(role.getNameRole()));
        }
        detailsDTO.setUsername(sa.getUsername());
        detailsDTO.setType(AccountType.STAFF);
        detailsDTO.setSuspended(sa.getLocked());
        detailsDTO.setRoleNames(roleNames);
        detailsDTO.setFullName(sa.getFullName());
        detailsDTO.setContactEmail(sa.getContactEmail());
        return detailsDTO;
    }

}
