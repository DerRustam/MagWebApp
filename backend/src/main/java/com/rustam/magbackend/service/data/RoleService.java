package com.rustam.magbackend.service.data;

import com.rustam.magbackend.dto.data.PrivilegeDTO;
import com.rustam.magbackend.dto.data.RoleDTO;
import com.rustam.magbackend.model.Privilege;
import com.rustam.magbackend.model.Role;
import com.rustam.magbackend.repository.IPrivilegeRepository;
import com.rustam.magbackend.repository.IRoleRepository;
import com.rustam.magbackend.utils.converter.IModelConverter;
import com.rustam.magbackend.utils.converter.PrivilegeConverter;
import com.rustam.magbackend.utils.converter.RoleConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private IPrivilegeRepository privilegeRepository;
    private IRoleRepository roleRepository;

    private final IModelConverter<Role, RoleDTO> roleConverter;
    private final IModelConverter<Privilege, PrivilegeDTO> privilegeConverter;

    @Autowired
    public RoleService(IPrivilegeRepository privilegeRepository, IRoleRepository roleRepository) {
        this.privilegeRepository = privilegeRepository;
        this.roleRepository = roleRepository;
        this.roleConverter = new RoleConverter();
        this.privilegeConverter = new PrivilegeConverter();
    }

    @PreAuthorize("hasAuthority('P_STRUCT_RD')")
    public Page<PrivilegeDTO> getPageViewPrivilege(int pageNum, int pageSize, String sortOrder){
        Page<Privilege> page = privilegeRepository.findAll(PageRequest.of(
                pageNum,
                pageSize,
                Sort.by((sortOrder.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC), "namePrivilege"))
        );
        return privilegeConverter.toPageDTO(page);
    }

    @PreAuthorize("hasAuthority('P_STRUCT_RD')")
    public List<PrivilegeDTO> getKeyListPrivilege(String namePrivilegeSearch, Integer limit){
        List<Privilege> list = privilegeRepository.findTopByNamePrivilegeContains(
                namePrivilegeSearch,
                PageRequest.of(0, limit));
        return privilegeConverter.toListKeyDTO(list);
    }

    @PreAuthorize("hasAuthority('P_STRUCT_RD')")
    public Page<RoleDTO> getPageViewRole(int pageNum, int pageSize, String sortOrder){
        Page<Role> page = roleRepository.findAll(PageRequest.of(
                pageNum,
                pageSize,
                Sort.by((sortOrder.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC), "nameRole"))
        );
        return roleConverter.toPageDTO(page);
    }

    @PreAuthorize("hasAuthority('P_STRUCT_RD')")
    public List<RoleDTO> getKeyListRole(String nameRoleSearch, Integer limit){
        List<Role> list = roleRepository.findTopByNameRoleContains(
                nameRoleSearch,
                PageRequest.of(0, limit));
        return roleConverter.toListKeyDTO(list);
    }
}
