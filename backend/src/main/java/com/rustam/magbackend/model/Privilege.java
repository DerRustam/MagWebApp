package com.rustam.magbackend.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "privilege")

public class Privilege {
    @Id
    @SequenceGenerator(name = "privilege_id_privilege_seq", sequenceName = "privilege_id_privilege_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "privilege_id_privilege_seq")
    @Column(name = "id_privilege", updatable = false, insertable = false)
    private Short id;

    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "name_privilege", nullable = false, unique = true, length = 20)
    private String namePrivilege;

    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "description_privilege", nullable = false, length = 200)
    private String descriptionPrivilege;

    @ManyToMany(mappedBy = "privileges", fetch = FetchType.LAZY)
    private List<Role> roles;

    public Privilege() {
    }

    public Privilege(@NotNull @Size(min = 1, max = 20) String namePrivilege, @NotNull @Size(min = 1, max = 100) String descriptionPrivilege, List<Role> roles) {
        this.namePrivilege = namePrivilege;
        this.descriptionPrivilege = descriptionPrivilege;
        this.roles = roles;
    }

    public Privilege(Short id, @NotNull @Size(min = 1, max = 20) String namePrivilege, @NotNull @Size(min = 1, max = 100) String descriptionPrivilege, List<Role> roles) {
        this.id = id;
        this.namePrivilege = namePrivilege;
        this.descriptionPrivilege = descriptionPrivilege;
        this.roles = roles;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getNamePrivilege() {
        return namePrivilege;
    }

    public void setNamePrivilege(String namePrivilege) {
        this.namePrivilege = namePrivilege;
    }

    public String getDescriptionPrivilege() {
        return descriptionPrivilege;
    }

    public void setDescriptionPrivilege(String descriptionPrivilege) {
        this.descriptionPrivilege = descriptionPrivilege;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Privilege)) return false;
        Privilege privilege = (Privilege) o;
        return getId().equals(privilege.getId()) &&
                getNamePrivilege().equals(privilege.getNamePrivilege());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
