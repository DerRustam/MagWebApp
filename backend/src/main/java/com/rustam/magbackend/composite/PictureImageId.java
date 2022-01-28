package com.rustam.magbackend.composite;

import com.rustam.magbackend.enums.ImageTypeSize;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
public class PictureImageId implements Serializable{
    private Long id;
    private ImageTypeSize typeSize;

    public PictureImageId() {
    }

    public PictureImageId(Long id, ImageTypeSize typeSize) {
        this.id = id;
        this.typeSize = typeSize;
    }
}
