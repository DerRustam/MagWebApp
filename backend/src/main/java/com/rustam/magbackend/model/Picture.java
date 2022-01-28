package com.rustam.magbackend.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;

@Entity
@DiscriminatorValue("0")
@Table(name = "picture")
@PrimaryKeyJoinColumn(name = "id_publication")
public class Picture extends Publication{
    @Size(min = 2, max = 5)
    @Column(name = "extension", length = 5)
    private String extension;

    @Size(min = 1, max = 300)
    @Column(name = "picture_description", length = 300)
    private String description;

    public Picture() {
    }

    public Picture(@NotNull Album album, @NotNull @Size(min = 1, max = 40) String namePublication, MatureRating matureRating, @NotNull Boolean isPublic, @NotNull Boolean isLocked, @NotNull Date datePublication, List<SearchTag> searchTags, @Size(min = 2, max = 5) String extension, @Size(min = 1, max = 300) String description) {
        super(album, namePublication, matureRating, isPublic, isLocked, datePublication, searchTags);
        this.extension = extension;
        this.description = description;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String pictureDescription) {
        this.description = pictureDescription;
    }
}
