package com.rustam.magbackend.service.data;

import com.rustam.magbackend.enums.DataConflictType;
import com.rustam.magbackend.enums.ImageTypeSize;
import com.rustam.magbackend.exception.DataServiceException;
import com.rustam.magbackend.model.PictureImage;
import com.rustam.magbackend.repository.IPictureImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ImageService {

    IPictureImageRepository repository;

    @Autowired
    public ImageService(IPictureImageRepository repository) {
        this.repository = repository;
    }

    @PreAuthorize("hasAuthority('P_PUBLICATION_RD')")
    public byte[] getImage(String filename){
        String[] parts = filename.split("-");
        if (parts.length != 2){
            throw new DataServiceException(DataConflictType.ARG_INVALID, filename);
        }
        if (parts[1].startsWith("thumb")){
            return getThumbnailImage(parts[0]);
        }
        if (parts[1].startsWith("view")){
            return getViewImage(parts[0]);
        }
        if (parts[1].startsWith("full")){
            return getFullImage(parts[0]);
        }
        throw new DataServiceException(DataConflictType.ARG_INVALID, filename);
    }

    @PreAuthorize("hasAuthority('P_PUBLICATION_RD')")
    public byte[] getThumbnailImage(String fileName) throws DataServiceException {
        try{
            Long id = Long.valueOf(fileName);
            PictureImage pictureImage = repository.findPictureImageByIdAndTypeSize(id, ImageTypeSize.THUMBNAIL)
                    .orElseThrow(() -> new DataServiceException(DataConflictType.NOT_FOUND, fileName));
            return pictureImage.getImage();
        } catch (IllegalArgumentException ex){
            throw new DataServiceException(DataConflictType.ARG_INVALID, fileName);
        }
    }

    @Transactional
    @PreAuthorize("hasAuthority('P_PUBLICATION_RD')")
    public byte[] getViewImage(String fileName) throws DataServiceException {
        try{
            Long id = Long.valueOf(fileName);
            List<PictureImage> list = repository.findPictureImagesByIdAndTypeSizeOrTypeSizeOrderByTypeSize(
                    id,
                    ImageTypeSize.VIEW,
                    ImageTypeSize.THUMBNAIL);

            if (list.size() == 0){
                throw new DataServiceException(DataConflictType.NOT_FOUND, fileName);
            } else if (list.size() == 1){
                return list.get(0).getImage();
            } else {
                return list.get(1).getImage();
            }
        } catch (IllegalArgumentException ex){
            throw new DataServiceException(DataConflictType.ARG_INVALID, fileName);
        }
    }

    @Transactional
    @PreAuthorize("hasAuthority('P_PUBLICATION_RD')")
    public byte[] getFullImage(String fileName) throws DataServiceException {
        try{
            Long id = Long.valueOf(fileName);
            List<PictureImage> list = repository.findPictureImagesById(id);
            switch (list.size()){
                case 0:{
                    throw new DataServiceException(DataConflictType.NOT_FOUND, fileName);
                }
                case 1:{
                    return list.get(0).getImage();
                } case 2:{
                    return list.get(1).getImage();
                }
                default:{
                    return list.get(2).getImage();
                }
            }
        } catch (IllegalArgumentException ex){
            throw new DataServiceException(DataConflictType.ARG_INVALID, fileName);
        }
    }
}
