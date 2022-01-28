package com.rustam.magbackend.repository;

import com.rustam.magbackend.model.UserAccount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserAccountRepository extends JpaRepository<UserAccount, Long> {
    List<UserAccount> findTopByViewNicknameContains(String viewNicknameLike, Pageable p);

    Optional<UserAccount> findUserAccountById(Long id);

    Optional<UserAccount> findUserAccountByViewNickname(String viewNickname);
}
