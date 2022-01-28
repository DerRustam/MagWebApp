package com.rustam.magbackend.service.user;

import com.rustam.magbackend.dto.AccountDetailsDTO;
import com.rustam.magbackend.dto.user.SignUpRequestPackage;
import com.rustam.magbackend.enums.PersonGender;
import com.rustam.magbackend.enums.DataConflictType;
import com.rustam.magbackend.exception.DataServiceException;
import com.rustam.magbackend.model.*;
import com.rustam.magbackend.repository.IAccountRepository;
import com.rustam.magbackend.repository.IAlbumRepository;
import com.rustam.magbackend.repository.IRoleRepository;
import com.rustam.magbackend.repository.IUserAccountRepository;
import com.rustam.magbackend.utils.converter.AccountDetailsConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    IRoleRepository roleRepository;
    IAccountRepository accountRepository;
    IUserAccountRepository userRepository;
    IAlbumRepository albumRepository;
    PasswordEncoder passwordEncoder;

    AccountDetailsConverter accountDetailsConverter;

    @Autowired
    public UserService(IRoleRepository roleRepository,
                       IAccountRepository accountRepository,
                       IUserAccountRepository userRepository,
                       IAlbumRepository albumRepository,
                       AccountDetailsConverter accountDetailsConverter,
                       PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.albumRepository = albumRepository;
        this.passwordEncoder = passwordEncoder;
        this.accountDetailsConverter = accountDetailsConverter;
    }


    @Transactional
    public String registerUser(SignUpRequestPackage requestPackage) throws DataServiceException {
        Optional<Account> existing = accountRepository.findAccountByUsernameOrEmail(requestPackage.getUsername(), requestPackage.getEmail());
        if (existing.isPresent()){
            StringBuilder sb = new StringBuilder();
            Account a = existing.get();
            if (a.getEmail().equals(requestPackage.getEmail())){
                throw new DataServiceException(DataConflictType.DUPLICATE, "email");
            }
            if (a.getUsername().equals(requestPackage.getUsername())){
                throw new DataServiceException(DataConflictType.DUPLICATE, "username");
            }
            throw new DataServiceException(DataConflictType.UNKNOWN,"???");
        } else{
            Role userDefaultRole = roleRepository.findRoleByNameRole("R_U_WATCH_SAFE").orElseThrow(RuntimeException::new);
            PersonGender gender = null;
            if (requestPackage.getGender()!= null && requestPackage.getGender().equals("M")){
                gender = PersonGender.M;
            } else if (requestPackage.getGender()!= null && requestPackage.getGender().equals("F")){
                gender = PersonGender.F;
            }
            List<Role> userRole = new ArrayList<Role>(){{add(userDefaultRole);}};
            UserAccount userAccount = new UserAccount(
                    requestPackage.getUsername(),
                    requestPackage.getEmail(),
                    passwordEncoder.encode(requestPackage.getPassword()),
                    false,
                    userRole,
                    requestPackage.getFirstName()+requestPackage.getUsername().hashCode(),
                    requestPackage.getFirstName(),
                    null,
                    null,
                    gender,
                    null
            );
            userAccount = userRepository.save(userAccount);
            Album a = new Album(userAccount, "Default", Date.valueOf(LocalDate.now()), "Default Album");
            albumRepository.save(a);
            return "Success";
        }
    }

    @PreAuthorize("#username == authentication.principal.username")
    public AccountDetailsDTO getAccountDetails(String username) throws DataServiceException{
        Account a = accountRepository.findAccountByUsername(username)
                .orElseThrow(()-> new DataServiceException(DataConflictType.NOT_FOUND, "username"));
        if (a instanceof UserAccount){
            return accountDetailsConverter.toDTO((UserAccount)a);
        } else if (a instanceof StaffAccount){
            return accountDetailsConverter.toDTO((StaffAccount) a);
        }
        throw new DataServiceException(DataConflictType.UNKNOWN, "???");
    }
}
