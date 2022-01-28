package com.rustam.magbackend.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "maturerating")
public class MatureRating {

    @Id
    @SequenceGenerator(name = "maturerating_id_mature_rating_seq", sequenceName = "maturerating_id_mature_rating_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "maturerating_id_mature_rating_seq")
    @Column(name = "id_mature_rating", updatable = false, insertable = false)
    private Short id;

    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "name_rating", length = 5, nullable = false, unique = true)
    private String nameRating;

    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "description_rating", length = 500, nullable = false)
    private String descriptionRating;

    public MatureRating() {
    }

    public MatureRating(@NotNull @Size(min = 1, max = 5) String nameRating, @NotNull @Size(min = 1, max = 500) String descriptionRating) {
        this.nameRating = nameRating;
        this.descriptionRating = descriptionRating;
    }

    public MatureRating(Short id, @NotNull @Size(min = 1, max = 5) String nameRating, @NotNull @Size(min = 1, max = 500) String descriptionRating) {
        this.id = id;
        this.nameRating = nameRating;
        this.descriptionRating = descriptionRating;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getNameRating() {
        return nameRating;
    }

    public void setNameRating(String ratingName) {
        this.nameRating = ratingName;
    }

    public String getDescriptionRating() {
        return descriptionRating;
    }

    public void setDescriptionRating(String ratingDescription) {
        this.descriptionRating = ratingDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MatureRating)) return false;
        MatureRating matureRating = (MatureRating) o;
        return getId().equals(matureRating.getId()) &&
                getNameRating().equals(matureRating.getNameRating());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
