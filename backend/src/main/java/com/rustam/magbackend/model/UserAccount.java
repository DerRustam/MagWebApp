package com.rustam.magbackend.model;

import com.rustam.magbackend.enums.PersonGender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
@DiscriminatorValue("1")
@Table(name = "useraccount")
@PrimaryKeyJoinColumn(name = "id_account")
public class UserAccount extends Account{

    @NotNull
    @Size(min = 2, max = 32)
    @Column(name = "view_nickname", length = 32, nullable = false, unique = true)
    private String viewNickname;

    @NotNull
    @Size(min = 1, max = 28)
    @Column(name = "first_name", length = 30, nullable = false)
    private String firstName;

    @Size(max = 40)
    @Column(name = "last_name", length = 40)
    private String lastName;

    @Column(name = "date_birth")
    private Date dateBirth;

    @Column(name = "gender", length = 1)
    @Enumerated(EnumType.STRING)
    private PersonGender gender;

    @Size(max = 300)
    @Column(length = 300)
    private String about;

    public UserAccount() {
    }

    public UserAccount(@NotNull @Size(min = 6, max = 32) String username, @NotNull @Email String email, @NotNull @Size(min = 1, max = 60) String encryptedPassword, @NotNull Boolean isLocked, List<Role> roles, @NotNull @Size(min = 2, max = 32) String viewNickname, @NotNull @Size(min = 1, max = 28) String firstName, @Size(max = 40) String lastName, Date dateBirth, PersonGender gender, @Size(max = 300) String about) {
        super(username, email, encryptedPassword, isLocked, roles);
        this.viewNickname = viewNickname;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateBirth = dateBirth;
        this.gender = gender;
        this.about = about;
    }

    public UserAccount(Long id, @NotNull @Size(min = 6, max = 32) String username, @NotNull @Email String email, @NotNull @Size(min = 1, max = 60) String encryptedPassword, @NotNull Boolean isLocked, List<Role> roles, @NotNull @Size(min = 2, max = 32) String viewNickname, @NotNull @Size(min = 1, max = 30) String firstName, @Size(max = 40) String lastName, Date dateBirth, PersonGender gender, @Size(max = 300) String about) {
        super(id, username, email, encryptedPassword, isLocked, roles);
        this.viewNickname = viewNickname;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateBirth = dateBirth;
        this.gender = gender;
        this.about = about;
    }

    public String getViewNickname() {
        return viewNickname;
    }

    public void setViewNickname(String viewNickname) {
        this.viewNickname = viewNickname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public PersonGender getGender() {
        return gender;
    }

    public void setGender(PersonGender gender) {
        this.gender = gender;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount)) return false;
        if (!super.equals(o)) return false;
        UserAccount that = (UserAccount) o;
        return getViewNickname().equals(that.getViewNickname()) &&
                getFirstName().equals(that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getDateBirth(), that.getDateBirth()) &&
                getGender() == that.getGender() &&
                Objects.equals(getAbout(), that.getAbout());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getViewNickname());
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "viewNickname='" + viewNickname + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateBirth=" + dateBirth +
                ", gender=" + gender +
                ", about='" + about + '\'' +
                "} " + super.toString();
    }
}
