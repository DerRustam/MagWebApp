package com.rustam.magbackend.repository;

import com.rustam.magbackend.model.Picture;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IPublicationPictureRepository extends IPublicationBaseRepository<Picture>{
}
