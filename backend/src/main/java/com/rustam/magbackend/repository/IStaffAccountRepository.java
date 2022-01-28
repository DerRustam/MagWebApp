package com.rustam.magbackend.repository;

import com.rustam.magbackend.model.StaffAccount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface IStaffAccountRepository extends JpaRepository<StaffAccount, Long> {
    List<StaffAccount> findTopByStaffMemberUidContains(String staffMemberUidLike, Pageable p);

    Optional<StaffAccount> findStaffAccountById(Long id);

    Optional<StaffAccount> findStaffAccountByStaffMemberUid(String staffMemberUid);
}
