package com.rustam.magbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "account")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "discrim_account", discriminatorType = DiscriminatorType.INTEGER, columnDefinition = "int2 (Types#SMALLINT)")
public abstract class Account {
    @Id
    @SequenceGenerator(name = "account_id_account_seq", sequenceName = "account_id_account_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_account_seq")
    @Column(name = "id_account", updatable = false, insertable = false)
    private Long id;

    @NotNull
    @Size(min = 6, max = 32)
    @Column(length = 32, nullable = false, unique = true)
    private String username;

    @NotNull
    @Email
    @Column(length = 254, nullable = false, unique = true)
    private String email;

    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private @JsonIgnore String encryptedPassword;

    @NotNull
    @Column(name = "is_locked", nullable = false)
    private Boolean isLocked;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "accountrole",
            joinColumns = @JoinColumn(name = "id_account", referencedColumnName = "id_account"),
            inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id_role")
    )
    private List<Role> roles;

    public Account() {
    }

    public Account(Long id, @NotNull @Size(min = 6, max = 32) String username, @NotNull @Email String email, @NotNull @Size(min = 1, max = 60) String encryptedPassword, @NotNull Boolean isLocked, List<Role> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.isLocked = isLocked;
        this.roles = roles;
    }

    public Account(@NotNull @Size(min = 6, max = 32) String username, @NotNull @Email String email, @NotNull @Size(min = 1, max = 60) String encryptedPassword, @NotNull Boolean isLocked, List<Role> roles) {
        this.username = username;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.isLocked = isLocked;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean isLocked) {
        isLocked = isLocked;
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
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return getId().equals(account.getId()) &&
                getUsername().equals(account.getUsername()) &&
                getEmail().equals(account.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", isLocked=" + isLocked +
                ", roles=" + roles +
                '}';
    }
}
