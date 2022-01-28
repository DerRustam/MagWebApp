package com.rustam.magbackend.dto.data;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "publicationType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PicturePublicationDTO.class, name = "picture"),
        @JsonSubTypes.Type(value = NotePublicationDTO.class, name = "note")
})
public abstract class PublicationDTO {

    private Long id;
    @NotNull
    private AlbumDTO album;
    @NotEmpty
    @Size(min = 1, max = 40)
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", flags = Pattern.Flag.UNICODE_CASE)
    private String namePublication;
    private MatureRatingDTO matureRating;
    @NotNull
    private Boolean isPublic;
    @NotNull
    private Boolean isLocked;
    @NotNull
    private Date datePublication;
    private List<SearchTagDTO> searchTags;

    public PublicationDTO() {
    }

    public PublicationDTO(Long id, AlbumDTO album, String namePublication) {
        this.id = id;
        this.album = album;
        this.namePublication = namePublication;
    }

    public PublicationDTO(Long id, String namePublication, MatureRatingDTO matureRating, Boolean isPublic, Boolean isLocked, Date datePublication) {
        this.id = id;
        this.namePublication = namePublication;
        this.matureRating = matureRating;
        this.isPublic = isPublic;
        this.isLocked = isLocked;
        this.datePublication = datePublication;
    }

    public PublicationDTO(Long id, AlbumDTO album, String namePublication, MatureRatingDTO matureRating, Boolean isPublic, Boolean isLocked, Date datePublication, List<SearchTagDTO> searchTags) {
        this.id = id;
        this.album = album;
        this.namePublication = namePublication;
        this.matureRating = matureRating;
        this.isPublic = isPublic;
        this.isLocked = isLocked;
        this.datePublication = datePublication;
        this.searchTags = searchTags;
    }
}
