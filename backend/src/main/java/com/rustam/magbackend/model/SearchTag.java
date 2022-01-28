package com.rustam.magbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "searchtag")
public class SearchTag {
    @Id
    @SequenceGenerator(
            name = "searchtag_id_tag_seq",
            sequenceName = "searchtag_id_tag_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "searchtag_id_tag_seq")
    @Column(name = "id_tag", updatable = false, insertable = false)
    private Long id;

    @NotNull
    @Size(min = 3, max = 16)
    @Column(name = "name_tag", length = 16, nullable = false, unique = true)
    private String nameTag;

    @ManyToMany(mappedBy = "searchTags", fetch = FetchType.LAZY)
    private List<Publication> publications;

    public SearchTag() {
    }

    public SearchTag(Long id, @NotNull @Size(min = 3, max = 16) String nameTag, List<Publication> publications) {
        this.id = id;
        this.nameTag = nameTag;
        this.publications = publications;
    }

    public SearchTag(@NotNull @Size(min = 3, max = 16) String nameTag, List<Publication> publications) {
        this.nameTag = nameTag;
        this.publications = publications;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameTag() {
        return nameTag;
    }

    public void setNameTag(String nameTag) {
        this.nameTag = nameTag;
    }

    public List<Publication> getPublications() {
        return publications;
    }

    public void setPublications(List<Publication> publications) {
        this.publications = publications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SearchTag)) return false;
        SearchTag searchTag = (SearchTag) o;
        return getId().equals(searchTag.getId()) &&
                getNameTag().equals(searchTag.getNameTag());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "SearchTag{" +
                "id=" + id +
                ", nameTag='" + nameTag + '\'' +
                '}';
    }
}
