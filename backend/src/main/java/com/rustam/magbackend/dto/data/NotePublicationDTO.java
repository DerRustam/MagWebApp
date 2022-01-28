package com.rustam.magbackend.dto.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class NotePublicationDTO extends PublicationDTO{
    private String textNote;

    public NotePublicationDTO(Long id, AlbumDTO albumDTO, String namePublication) {
        super(id, albumDTO, namePublication);
    }

    public NotePublicationDTO(Long id, String namePublication, MatureRatingDTO matureRating, Boolean isPublic, Boolean isLocked, Date datePublication) {
        super(id, namePublication, matureRating, isPublic, isLocked, datePublication);
    }

    public NotePublicationDTO(Long id, String namePublication, MatureRatingDTO matureRating, Boolean isPublic, Boolean isLocked, Date datePublication, String textNote) {
        super(id, namePublication, matureRating, isPublic, isLocked, datePublication);
        this.textNote = textNote;
    }

    public NotePublicationDTO(Long id, AlbumDTO albumDTO, String namePublication, MatureRatingDTO matureRating, Boolean isPublic, Boolean isLocked, Date datePublication, List<SearchTagDTO> tags, String textNote) {
        super(id, albumDTO, namePublication, matureRating, isPublic, isLocked, datePublication, tags);
        this.textNote = textNote;
    }
}
