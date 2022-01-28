package com.rustam.magbackend.repository;

import com.rustam.magbackend.model.Album;
import com.rustam.magbackend.model.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface IPublicationBaseRepository <T extends Publication> extends JpaRepository<T, Long> {
    @Query("select pub from #{#entityName} as pub where pub.album.id = ?1 and pub.namePublication like %?2%")
    List<T> findPublicationsByAlbum_IdAndNamePublication(Long albumId, String namePublicationLike, Pageable p);

    @Query("select pub from #{#entityName} as pub where pub.album = ?1 and pub.namePublication like %?2%")
    List<T> findPublicationsByAlbumAndNamePublication(Album album, String namePublicationLike, Pageable p);

    @Query("select pub from #{#entityName} as pub where pub.album = ?1 and pub.namePublication = ?2")
    Optional<T> findPublicationByAlbumAndNamePublication(Album album, String namePublication);

    @Query("update #{#entityName} as pub set pub.album.id = ?1, pub.namePublication = pub.id," +
            "pub.isPublic = false, pub.isLocked = true where pub.album = ?2")
    void moveAlbumPublicationsToArchive(Long voidAlbumId, Album album);
}
