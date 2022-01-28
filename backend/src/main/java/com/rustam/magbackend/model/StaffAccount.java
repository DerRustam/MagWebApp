package com.rustam.magbackend.model;

import com.rustam.magbackend.dto.data.StaffAccountDTO;
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
@DiscriminatorValue("0")
@Table(name = "staffaccount")
@PrimaryKeyJoinColumn(name = "id_account")

public class StaffAccount extends Account{

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "id_staff_member", length = 50, nullable = false, unique = true)
    private String staffMemberUid;

    @NotNull
    @Email
    @Column(name = "contact_email", length = 254, nullable = false)
    private String contactEmail;

    @Size(max = 20)
    @Column(name = "contact_phone", length = 20)
    private String contactPhone;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "full_name", length = 100, nullable = false)
    private String fullName;

    @NotNull
    @Column(name = "date_hire", nullable = false)
    private Date dateHire;

    @NotNull
    @Column(name = "date_birth", nullable = false)
    private Date dateBirth;

    @NotNull
    @Column(name = "gender", length = 1, nullable = false)
    @Enumerated(EnumType.STRING)
    private PersonGender gender;

    public StaffAccount() {
    }

    public StaffAccount(@NotNull @Size(min = 6, max = 32) String username, @NotNull @Email String email, @NotNull @Size(min = 1, max = 60) String encryptedPassword, @NotNull Boolean isLocked, List<Role> roles, @NotNull @Size(min = 2, max = 50) String staffMemberUid, @NotNull @Email String contactEmail, @Size(max = 20) String contactPhone, @NotNull @Size(min = 1, max = 100) String fullName, @NotNull Date dateHire, @NotNull Date dateBirth, @NotNull PersonGender gender) {
        super(username, email, encryptedPassword, isLocked, roles);
        this.staffMemberUid = staffMemberUid;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.fullName = fullName;
        this.dateHire = dateHire;
        this.dateBirth = dateBirth;
        this.gender = gender;
    }

    public StaffAccount(Long id, @NotNull @Size(min = 6, max = 32) String username, @NotNull @Email String email, @NotNull @Size(min = 1, max = 60) String encryptedPassword, @NotNull Boolean isLocked, List<Role> roles, @NotNull @Size(min = 2, max = 50) String staffMemberUid, @NotNull @Email String contactEmail, @Size(max = 20) String contactPhone, @NotNull @Size(min = 1, max = 100) String fullName, @NotNull Date dateHire, @NotNull Date dateBirth, @NotNull PersonGender gender) {
        super(id, username, email, encryptedPassword, isLocked, roles);
        this.staffMemberUid = staffMemberUid;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.fullName = fullName;
        this.dateHire = dateHire;
        this.dateBirth = dateBirth;
        this.gender = gender;
    }

    public String getStaffMemberUid() {
        return staffMemberUid;
    }

    public void setStaffMemberUid(String staffMemberUid) {
        this.staffMemberUid = staffMemberUid;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateHire() {
        return dateHire;
    }

    public void setDateHire(Date dateHire) {
        this.dateHire = dateHire;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StaffAccount)) return false;
        if (!super.equals(o)) return false;
        StaffAccount account = (StaffAccount) o;
        return staffMemberUid.equals(account.staffMemberUid) &&
                contactEmail.equals(account.contactEmail) &&
                Objects.equals(contactPhone, account.contactPhone) &&
                fullName.equals(account.fullName) &&
                dateHire.equals(account.dateHire) &&
                dateBirth.equals(account.dateBirth) &&
                gender == account.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), staffMemberUid);
    }

    @Override
    public String toString() {
        return "StaffAccount{" +
                "staffMemberUid='" + staffMemberUid + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", fullName='" + fullName + '\'' +
                ", dateHire=" + dateHire +
                ", dateBirth=" + dateBirth +
                ", gender=" + gender +
                "} " + super.toString();
    }
}
