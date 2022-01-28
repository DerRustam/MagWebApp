package com.rustam.magbackend.repository;

import com.rustam.magbackend.model.Publication;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IPublicationRepository extends IPublicationBaseRepository<Publication>{

}
