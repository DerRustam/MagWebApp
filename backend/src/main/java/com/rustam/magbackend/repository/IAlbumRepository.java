package com.rustam.magbackend.repository;

import com.rustam.magbackend.model.Album;
import com.rustam.magbackend.model.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findAllByUserAccount(UserAccount ua);

    List<Album> findTopByUserAccount_IdAndNameAlbumContains(Long accountId, String nameAlbumLike, Pageable p);
    List<Album> findTopByUserAccount_ViewNicknameAndNameAlbumContains(String viewNickname, String nameAlbumLike, Pageable p);

    Optional<Album> findAlbumByUserAccountAndNameAlbum(UserAccount ua, String nameAlbum);
    @Query("select a from Album a where a.userAccount.viewNickname='??' and a.nameAlbum='Void Archive'")
    Optional<Album> findVoidAlbum();

    //Page<Album> findAll(Pageable pageable);
}
