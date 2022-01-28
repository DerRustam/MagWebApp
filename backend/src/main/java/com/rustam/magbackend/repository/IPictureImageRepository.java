package com.rustam.magbackend.repository;

import com.rustam.magbackend.composite.PictureImageId;
import com.rustam.magbackend.enums.ImageTypeSize;
import com.rustam.magbackend.model.PictureImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPictureImageRepository extends JpaRepository<PictureImage, PictureImageId> {
    Optional<PictureImage> findPictureImageByIdAndTypeSize(Long id, ImageTypeSize typeSize);
    List<PictureImage> findPictureImagesByIdAndTypeSizeOrTypeSizeOrderByTypeSize(
            Long id,
            ImageTypeSize typeSizeHigh,
            ImageTypeSize typeSizeLow);
    List<PictureImage> findPictureImagesById(Long id);

    void deleteAllById(Long id);
}
