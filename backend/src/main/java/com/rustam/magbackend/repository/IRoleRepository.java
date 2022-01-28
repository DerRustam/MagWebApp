package com.rustam.magbackend.repository;

import com.rustam.magbackend.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Short> {

    List<Role> findTopByNameRoleContains(String nameRoleLike, Pageable p);

    Optional<Role> findRoleByNameRole(String nameRole);
}
