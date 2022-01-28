package com.rustam.magbackend.model;

import com.rustam.magbackend.dto.data.PrivilegeDTO;
import com.rustam.magbackend.dto.data.RoleDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @SequenceGenerator(name = "role_id_role_seq", sequenceName = "role_id_role_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_role_seq")
    @Column(name = "id_role", updatable = false, insertable = false)
    private Short id;

    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "name_role", nullable = false, unique = true, length = 20)
    private String nameRole;

    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "description_role", nullable = false, length = 200)
    private String descriptionRole;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<Account> accounts;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "roleprivilege",
            joinColumns = @JoinColumn(name = "id_role",  referencedColumnName = "id_role"),
            inverseJoinColumns = @JoinColumn(name = "id_privilege", referencedColumnName = "id_privilege")
    )
    private List<Privilege> privileges;

    public Role() {
    }

    public Role(@NotNull @Size(min = 1, max = 20) String nameRole, @NotNull @Size(min = 1, max = 100) String descriptionRole, List<Account> accounts, List<Privilege> privileges) {
        this.nameRole = nameRole;
        this.descriptionRole = descriptionRole;
        this.accounts = accounts;
        this.privileges = privileges;
    }

    public Role(Short id, @NotNull @Size(min = 1, max = 20) String nameRole, @NotNull @Size(min = 1, max = 100) String descriptionRole, List<Account> accounts, List<Privilege> privileges) {
        this.id = id;
        this.nameRole = nameRole;
        this.descriptionRole = descriptionRole;
        this.accounts = accounts;
        this.privileges = privileges;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    public String getDescriptionRole() {
        return descriptionRole;
    }

    public void setDescriptionRole(String descriptionRole) {
        this.descriptionRole = descriptionRole;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return getId().equals(role.getId()) &&
                getNameRole().equals(role.getNameRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
