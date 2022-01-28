package com.rustam.magbackend.repository;

import com.rustam.magbackend.model.Account;
import com.rustam.magbackend.model.History;
import com.rustam.magbackend.model.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface IHistoryRepository extends JpaRepository<History, Long> {
    List<History> findTopByAccount_IdAndPublication_IdAndDatetimeIssueAfter(
            Long accountId,
            Long publicationId,
            Timestamp dateAfter,
            Pageable p
            );

    Optional<History> findByAccountAndPublication(Account account, Publication publication);

    Optional<History> findByAccountAndPublicationAndDatetimeIssue(Account account, Publication publication, Timestamp datetimeIssue);

    @Query("SELECT h from History h, StaffAccount sa where h.account.id = sa.id")
    Page<History> findAllStaffHistoryPage(Pageable p);

    @Query("SELECT h from History h, UserAccount ua where h.account.id = ua.id")
    Page<History> findAllUserHistoryPage(Pageable p);
}
