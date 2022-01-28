package com.rustam.magbackend.repository;

import com.rustam.magbackend.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findAccountByUsernameOrEmail(String username, String email);

    Optional<Account> findAccountByUsername(String username);

    Optional<Account> findAccountByEmail(String email);
}
