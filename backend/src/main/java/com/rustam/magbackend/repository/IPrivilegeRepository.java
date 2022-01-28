package com.rustam.magbackend.repository;

import com.rustam.magbackend.dto.data.PrivilegeDTO;
import com.rustam.magbackend.model.Privilege;
import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPrivilegeRepository extends JpaRepository<Privilege, Short> {

    List<Privilege> findTopByNamePrivilegeContains(String namePrivilegeLike, Pageable p);

    Optional<Privilege> findPrivilegeByNamePrivilege(String namePrivilege);
    //Page<Privilege> findAll(@NonNull Pageable pageable);
}
