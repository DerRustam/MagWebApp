package com.rustam.magbackend.repository;

import com.rustam.magbackend.model.Note;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IPublicationNoteRepository extends IPublicationBaseRepository<Note>{
}
