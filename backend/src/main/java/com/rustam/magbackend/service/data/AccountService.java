package com.rustam.magbackend.service.data;

import com.rustam.magbackend.dto.data.AccountDTO;
import com.rustam.magbackend.dto.data.RoleDTO;
import com.rustam.magbackend.dto.data.StaffAccountDTO;
import com.rustam.magbackend.dto.data.UserAccountDTO;
import com.rustam.magbackend.enums.DataConflictType;
import com.rustam.magbackend.exception.DataServiceException;
import com.rustam.magbackend.model.*;
import com.rustam.magbackend.repository.*;
import com.rustam.magbackend.utils.converter.IModelConverter;
import com.rustam.magbackend.utils.converter.StaffAccountConverter;
import com.rustam.magbackend.utils.converter.UserAccountConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Service
public class AccountService {
    IUserAccountRepository userRepository;
    IStaffAccountRepository staffRepository;
    IAlbumRepository albumRepository;
    IPublicationBaseRepository<Publication> publicationRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(IUserAccountRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setStaffRepository(IStaffAccountRepository staffRepository) {
        this.staffRepository = staffRepository;
    }
    @Autowired
    public void setAlbumRepository(IAlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }
    @Autowired
    public void setPublicationRepository(IPublicationBaseRepository<Publication> publicationRepository) {
        this.publicationRepository = publicationRepository;
    }
    @Autowired
    public void setEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private final IModelConverter<StaffAccount, StaffAccountDTO> staffConverter = new StaffAccountConverter();
    private final IModelConverter<UserAccount, UserAccountDTO> userConverter = new UserAccountConverter();

    @PreAuthorize("hasAuthority('P_STAFF_RD')")
    public Page<StaffAccountDTO> getPageViewStaffAccount(int pageNum, int pageSize, String sortOrder, String sortField) {
        Page<StaffAccount> page = staffRepository.findAll(PageRequest.of(
                pageNum,
                pageSize,
                Sort.by((sortOrder.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC), sortField)
        ));

        return staffConverter.toPageDTO(page);
    }

    @PreAuthorize("hasAuthority('P_STAFF_RD')")
    public List<StaffAccountDTO> getKeyListStaffAccount(String staffMemberUidSearch, Integer limit){
        List<StaffAccount> list = staffRepository.findTopByStaffMemberUidContains(
                staffMemberUidSearch,
                PageRequest.of(0, limit)
        );
        return staffConverter.toListKeyDTO(list);
    }

    @PreAuthorize("hasAuthority('P_USER_RD')")
    public Page<UserAccountDTO> getPageViewUserAccount(int pageNum, int pageSize, String sortOrder, String sortField) {
        Page<UserAccount> page = userRepository.findAll(PageRequest.of(
                pageNum,
                pageSize,
                Sort.by((sortOrder.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC), sortField)
        ));
        return userConverter.toPageDTO(page);
    }

    @PreAuthorize("hasAuthority('P_USER_RD')")
    public List<UserAccountDTO> getKeyListUserAccount(String viewNicknameSearch, Integer limit){
        List<UserAccount> list = userRepository.findTopByViewNicknameContains(
                viewNicknameSearch,
                PageRequest.of(0,limit)
        );
        return userConverter.toListKeyDTO(list);
    }

    @Transactional
    @PreAuthorize("hasAuthority('P_USER_WR')")
    public UserAccountDTO addUserAccount(UserAccountDTO dto) throws DataServiceException {
        List<RoleDTO> dtoList = sortFilterDtoRoles(dto.getRoles());
        List<Role> list = loadRoles(dtoList);
        UserAccount ua = new UserAccount(
                dto.getUsername(),
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getIsLocked(),
                list,
                dto.getViewNickname(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getDateBirth(),
                dto.getGender(),
                dto.getAbout()
        );
        ua = userRepository.save(ua);
        Album a = new Album(ua, "Default", Date.valueOf(LocalDate.now()), "Default album");
        albumRepository.save(a);
        return userConverter.toDTO(ua);
    }

    @Transactional
    @PreAuthorize("hasAuthority('P_USER_WR')")
    public UserAccountDTO updateUserAccount(UserAccountDTO dto) throws DataServiceException {
        UserAccount ua;
        if (dto.getId() != null){
            ua = userRepository.findUserAccountById(dto.getId()).orElseThrow(
                    () -> new DataServiceException(DataConflictType.NOT_FOUND, dto.toString()));
        } else {
            ua = userRepository.findUserAccountByViewNickname(dto.getViewNickname()).orElseThrow(
                    ()-> new DataServiceException(DataConflictType.NOT_FOUND, dto.toString()));
        }
        ua.setLocked(dto.getIsLocked());
        List<RoleDTO> dtoList = sortFilterDtoRoles(dto.getRoles());
        if (isRolesChanged(ua.getRoles(), dtoList)){
            List<Role> list = loadRoles(dtoList);
            ua.setRoles(list);
        }
        ua.setViewNickname(dto.getNewViewNickName());
        ua.setFirstName(dto.getFirstName());
        ua.setLastName(dto.getLastName());
        ua.setDateBirth(dto.getDateBirth());
        ua.setGender(dto.getGender());
        ua.setAbout(dto.getAbout());
        if (dto.getPassword() != null){
            updateCredentials(ua, dto);
        }
        ua = userRepository.save(ua);
        return userConverter.toDTO(ua);
    }

    @Transactional
    @PreAuthorize("hasAuthority('P_USER_WR')")
    public void deleteUserAccount(Long id) throws DataServiceException{
        UserAccount ua = userRepository.findUserAccountById(id).orElseThrow(
                () -> new DataServiceException(DataConflictType.NOT_FOUND, String.valueOf(id)));
        Album voidAlbum = albumRepository.findVoidAlbum().orElseThrow(
                ()-> new DataServiceException(DataConflictType.UNKNOWN, "Void album not found")
        );
        if(ua.getViewNickname().equals("??")){
            throw new DataServiceException(DataConflictType.ARG_INVALID, "Can't remove archive user");
        }
        List<Album> albums = albumRepository.findAllByUserAccount(ua);
        Iterator<Album> iter = albums.iterator();
        while (iter.hasNext()){
            Album album = iter.next();
            publicationRepository.moveAlbumPublicationsToArchive(voidAlbum.getId(), album);
        }
        userRepository.delete(ua);
    }

    @Transactional
    @PreAuthorize("hasAuthority('P_STAFF_WR')")
    public StaffAccountDTO addStaffAccount(StaffAccountDTO dto) throws DataServiceException {
        List<RoleDTO> dtoList = sortFilterDtoRoles(dto.getRoles());
        List<Role> list = loadRoles(dtoList);
        StaffAccount sa = new StaffAccount(
                dto.getUsername(),
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getIsLocked(),
                list,
                dto.getStaffMemberUid(),
                dto.getContactEmail(),
                dto.getContactPhone(),
                dto.getFullName(),
                dto.getDateHire(),
                dto.getDateBirth(),
                dto.getGender()
        );
        sa = staffRepository.save(sa);
        return staffConverter.toDTO(sa);
    }

    @Transactional
    @PreAuthorize("hasAuthority('P_STAFF_WR')")
    public StaffAccountDTO updateStaffAccount(StaffAccountDTO dto) throws DataServiceException {
        StaffAccount sa;
        if (dto.getId() != null){
            sa = staffRepository.findStaffAccountById(dto.getId()).orElseThrow(
                    () -> new DataServiceException(DataConflictType.NOT_FOUND, dto.toString()));
        } else {
            sa = staffRepository.findStaffAccountByStaffMemberUid(dto.getStaffMemberUid()).orElseThrow(
                    ()-> new DataServiceException(DataConflictType.NOT_FOUND, dto.toString()));
        }
        sa.setLocked(dto.getIsLocked());
        List<RoleDTO> dtoList = sortFilterDtoRoles(dto.getRoles());
        if (isRolesChanged(sa.getRoles(), dtoList)){
            List<Role> list = loadRoles(dtoList);
            sa.setRoles(list);
        }
        sa.setStaffMemberUid(dto.getStaffMemberUid());
        sa.setContactEmail(dto.getContactEmail());
        sa.setContactPhone(dto.getContactPhone());
        sa.setFullName(dto.getFullName());
        sa.setDateHire(dto.getDateHire());
        sa.setDateBirth(dto.getDateBirth());
        sa.setGender(dto.getGender());
        if (dto.getPassword() != null){
            updateStaffCredentials(sa, dto);
        }
        sa = staffRepository.save(sa);
        return staffConverter.toDTO(sa);
    }

    @PreAuthorize("hasRole('R_S_HEAD_ADMIN')")
    protected void updateStaffCredentials(StaffAccount sa, StaffAccountDTO dto){
        updateCredentials(sa, dto);
    }

    @PreAuthorize("hasAuthority('R_S_HEAD_ADMIN')")
    public void deleteStaffAccount(Long id) throws DataServiceException{
        if (!staffRepository.existsById(id)){
            throw new DataServiceException(DataConflictType.NOT_FOUND, String.valueOf(id));
        }
        staffRepository.deleteById(id);
    }

    private void updateCredentials(Account account, AccountDTO dto){
        String encoded = passwordEncoder.encode(dto.getPassword());
        if (dto.getUsername() != null && !account.getUsername().equals(dto.getUsername())) {
            throw new DataServiceException(DataConflictType.CREDENTIALS_INVALID, "Invalid credentials");
        } else if (dto.getEmail() == null && !account.getEmail().equals(dto.getEmail())){
            throw new DataServiceException(DataConflictType.CREDENTIALS_INVALID, "Invalid credentials");
        }
        if (!encoded.equals(account.getEncryptedPassword())){
            throw new DataServiceException(DataConflictType.CREDENTIALS_INVALID, "Invalid credentials");
        }
        if (dto.getNewEmail() != null){
            account.setEmail(dto.getNewEmail());
        }
        if (dto.getNewUsername() != null){
            account.setUsername(dto.getUsername());
        }
        if (dto.getNewPassword() != null){
            account.setEncryptedPassword(passwordEncoder.encode(dto.getNewPassword()));
        }
    }

    private List<Role> loadRoles(List<RoleDTO> dtoRoles) throws DataServiceException{
        List<Role> list = new ArrayList<>();
        for (RoleDTO rDto : dtoRoles){
            Role r = new Role();
            if (rDto.getId() != null){
                r.setId(rDto.getId());
                list.add(r);
            } else if (rDto.getNameRole() != null){
                r.setNameRole(rDto.getNameRole());
            }
            else {
                throw new DataServiceException(DataConflictType.NOT_FOUND, rDto.toString());
            }
        }
        return list;
    }

    private List<RoleDTO> sortFilterDtoRoles(List<RoleDTO> dtoRoles){
        if (dtoRoles == null){
            return new ArrayList<>();
        }
        dtoRoles.sort(Comparator.comparing(RoleDTO::getNameRole));
        Set<String> names = new HashSet<>();
        for (RoleDTO rDto : dtoRoles){
            names.add(rDto.getNameRole());
        }
        if (names.size() != dtoRoles.size()){
            List<RoleDTO> list = new ArrayList<>();
            for (RoleDTO rDto : dtoRoles){
                if (names.contains(rDto.getNameRole())){
                    list.add(rDto);
                    names.remove(rDto.getNameRole());
                }
            }
            return list;
        }
        return dtoRoles;
    }

    private boolean isRolesChanged(List<Role> roles, List<RoleDTO> dtoRoles){
        if (roles.size() != dtoRoles.size()){
            return true;
        } else {
            for (int i = 0; i< roles.size(); i++){
                if (!roles.get(i).getNameRole().equals(dtoRoles.get(i).getNameRole())){
                    return true;
                }
            }
        }
        return false;
    }
}
