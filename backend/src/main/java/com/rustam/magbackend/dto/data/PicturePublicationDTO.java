package com.rustam.magbackend.dto.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class PicturePublicationDTO extends PublicationDTO{
    private String description;
    private String extension;

    public PicturePublicationDTO(Long id, AlbumDTO albumDTO, String namePublication) {
        super(id, albumDTO, namePublication);
    }

    public PicturePublicationDTO(Long id, String namePublication, MatureRatingDTO matureRating, Boolean isPublic, Boolean isLocked, Date datePublication) {
        super(id, namePublication, matureRating, isPublic, isLocked, datePublication);
    }

    public PicturePublicationDTO(Long id, String namePublication, MatureRatingDTO matureRating, Boolean isPublic, Boolean isLocked, Date datePublication, String description) {
        super(id, namePublication, matureRating, isPublic, isLocked, datePublication);
        this.description = description;
    }

    public PicturePublicationDTO(Long id, AlbumDTO albumDTO, String namePublication, MatureRatingDTO matureRatingDTO, Boolean isPublic, Boolean isLocked, Date datePublication, List<SearchTagDTO> tags, String description, String extension) {
        super(id, albumDTO, namePublication, matureRatingDTO, isPublic, isLocked, datePublication, tags);
        this.description = description;
        this.extension = extension;
    }
}
