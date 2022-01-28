package com.rustam.magbackend.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;

@Entity
@Table(
        name = "publication",
        uniqueConstraints = {@UniqueConstraint(name = "albumAndNamePublication", columnNames ={"id_album", "name_publication"})}
        )
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "discrim_publication", discriminatorType = DiscriminatorType.INTEGER, columnDefinition = "int2 (Types#SMALLINT)")
public abstract class Publication {
    @Id
    @SequenceGenerator(
            name = "publication_id_publication_seq",
            sequenceName = "publication_id_publication_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publication_id_publication_seq")
    @Column(name = "id_publication", updatable = false, insertable = false)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_album", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Album album;

    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "name_publication", length = 40, nullable = false)
    private String namePublication;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mature_rating")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private MatureRating matureRating;

    @NotNull
    @Column(name = "is_public", nullable = false)
    private Boolean isPublic;

    @NotNull
    @Column(name = "is_locked", nullable = false)
    private Boolean isLocked;

    @NotNull
    @Column(name = "date_publication", nullable = false)
    private Date datePublication;

    @ManyToMany
    @JoinTable(
            name = "publicationtag",
            joinColumns = @JoinColumn(name = "id_publication",  referencedColumnName = "id_publication"),
            inverseJoinColumns = @JoinColumn(name = "id_tag", referencedColumnName = "id_tag")
    )
    private List<SearchTag> searchTags;

    public Publication() {
    }

    public Publication(@NotNull Album album, @NotNull @Size(min = 1, max = 40) String namePublication, MatureRating matureRating, @NotNull Boolean isPublic, @NotNull Boolean isLocked, @NotNull Date datePublication, List<SearchTag> searchTags) {
        this.album = album;
        this.namePublication = namePublication;
        this.matureRating = matureRating;
        this.isPublic = isPublic;
        this.isLocked = isLocked;
        this.datePublication = datePublication;
        this.searchTags = searchTags;
    }

    public Publication(Long id, @NotNull Album album, @NotNull @Size(min = 1, max = 40) String namePublication, MatureRating matureRating, @NotNull Boolean isPublic, @NotNull Boolean isLocked, @NotNull Date datePublication, List<SearchTag> searchTags) {
        this.id = id;
        this.album = album;
        this.namePublication = namePublication;
        this.matureRating = matureRating;
        this.isPublic = isPublic;
        this.isLocked = isLocked;
        this.datePublication = datePublication;
        this.searchTags = searchTags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getNamePublication() {
        return namePublication;
    }

    public void setNamePublication(String namePublication) {
        this.namePublication = namePublication;
    }

    public MatureRating getMatureRating() {
        return matureRating;
    }

    public void setMatureRating(MatureRating matureRating) {
        this.matureRating = matureRating;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public Date getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(Date datePublication) {
        this.datePublication = datePublication;
    }

    public List<SearchTag> getSearchTags() {
        return searchTags;
    }

    public void setSearchTags(List<SearchTag> searchTags) {
        this.searchTags = searchTags;
    }
}
