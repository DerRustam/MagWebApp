package com.rustam.magbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(
        name = "album",
        uniqueConstraints = {@UniqueConstraint(columnNames ={"id_user", "name_album"})}
        )
public class Album {
    @Id
    @SequenceGenerator(name = "album_id_album_seq", sequenceName = "album_id_album_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "album_id_album_seq")
    @Column(name = "id_album", updatable = false, insertable = false)
    private Long id;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserAccount userAccount;

    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "name_album", length = 40, nullable = false)
    private String nameAlbum;

    @NotNull
    @Column(name = "date_creation", nullable = false)
    private Date dateCreation;

    @Size(max = 300)
    @Column(name="description_album",length = 300)
    private String descriptionAlbum;

    public Album() {
    }

    public Album(@NotNull UserAccount userAccount, @NotNull @Size(min = 1, max = 40) String nameAlbum, @NotNull Date dateCreation, @Size(max = 300) String descriptionAlbum) {
        this.userAccount = userAccount;
        this.nameAlbum = nameAlbum;
        this.dateCreation = dateCreation;
        this.descriptionAlbum = descriptionAlbum;
    }

    public Album(Long id, @NotNull UserAccount userAccount, @NotNull @Size(min = 1, max = 40) String nameAlbum, @NotNull Date dateCreation, @Size(max = 300) String descriptionAlbum) {
        this.id = id;
        this.userAccount = userAccount;
        this.nameAlbum = nameAlbum;
        this.dateCreation = dateCreation;
        this.descriptionAlbum = descriptionAlbum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public String getNameAlbum() {
        return nameAlbum;
    }

    public void setNameAlbum(String nameAlbum) {
        this.nameAlbum = nameAlbum;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDescriptionAlbum() {
        return descriptionAlbum;
    }

    public void setDescriptionAlbum(String descriptionAlbum) {
        this.descriptionAlbum = descriptionAlbum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Album)) return false;
        Album album = (Album) o;
        return getId().equals(album.getId()) &&
                getUserAccount().equals(album.getUserAccount()) &&
                getNameAlbum().equals(album.getNameAlbum());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", userAccount=" + userAccount +
                ", nameAlbum='" + nameAlbum + '\'' +
                ", dateCreation=" + dateCreation +
                ", descriptionAlbum='" + descriptionAlbum + '\'' +
                '}';
    }
}
