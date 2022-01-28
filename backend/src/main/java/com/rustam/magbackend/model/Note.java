package com.rustam.magbackend.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;

@Entity
@DiscriminatorValue("1")
@Table(name = "note")
@PrimaryKeyJoinColumn(name = "id_publication")

public class Note extends Publication{
    @Size(min = 1, max = 8192)
    @Column(name = "text_note", length = 8192)
    private String textNote;

    public Note() {
    }

    public Note(@NotNull Album album, @NotNull @Size(min = 1, max = 40) String namePublication, MatureRating matureRating, @NotNull Boolean isPublic, @NotNull Boolean isLocked, @NotNull Date datePublication, List<SearchTag> searchTags, @Size(min = 1, max = 8192) String textNote) {
        super(album, namePublication, matureRating, isPublic, isLocked, datePublication, searchTags);
        this.textNote = textNote;
    }

    public String getTextNote() {
        return textNote;
    }

    public void setTextNote(String textNote) {
        this.textNote = textNote;
    }
}
