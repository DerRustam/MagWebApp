package com.rustam.magbackend.model;

import com.rustam.magbackend.composite.PictureImageId;
import com.rustam.magbackend.enums.ImageTypeSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.internal.util.stereotypes.Lazy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@IdClass(PictureImageId.class)
@Table(name = "pictureimage")
public class PictureImage {
    @Id
    @NotNull
    @Column(name = "id_publication", nullable = false, updatable = false) //!!
    private Long id;

    @Id
    @NotNull
    @Column(name = "type_size", nullable = false, columnDefinition = "int2 (Types#SMALLINT)")
    @Enumerated(EnumType.ORDINAL)
    private ImageTypeSize typeSize;

    @NotNull
    @Lazy
    @Column(nullable = false)
    private byte[] image;

    public PictureImage() {
    }

    public PictureImage(@NotNull Long id, @NotNull ImageTypeSize typeSize, @NotNull byte[] image) {
        this.id = id;
        this.typeSize = typeSize;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ImageTypeSize getTypeSize() {
        return typeSize;
    }

    public void setTypeSize(ImageTypeSize typeSize) {
        this.typeSize = typeSize;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
