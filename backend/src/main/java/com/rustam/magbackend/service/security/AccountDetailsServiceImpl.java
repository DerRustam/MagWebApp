package com.rustam.magbackend.service.security;

import com.rustam.magbackend.model.Account;
import com.rustam.magbackend.model.Privilege;
import com.rustam.magbackend.model.Role;
import com.rustam.magbackend.repository.IAccountRepository;
import com.rustam.magbackend.security.AccountBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AccountDetailsServiceImpl implements UserDetailsService {

    private IAccountRepository accountRepository;

    @Autowired
    public void setAccountRepository(IAccountRepository repository){
        this.accountRepository = repository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account a;
        if (username.contains("@")){
            a = accountRepository.findAccountByEmail(username)
                    .orElseThrow(()->new UsernameNotFoundException("email"));
        } else {
            a = accountRepository.findAccountByUsername(username)
                    .orElseThrow(()-> new UsernameNotFoundException("username"));
        }
        return AccountBaseImpl.build(a, getAuthorities(a.getRoles()));
    }

    private List<? extends GrantedAuthority> getAuthorities(List<Role> roles){
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private Set<String> getPrivileges(List<Role> roles){
        Set<String> privileges = new HashSet<>();
        for (Role r : roles){
            for (Privilege p : r.getPrivileges()){
                privileges.add(p.getNamePrivilege());
            }
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(Set<String> privileges){
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String p : privileges){
            authorities.add(new SimpleGrantedAuthority(p));
        }
        return authorities;
    }
}
