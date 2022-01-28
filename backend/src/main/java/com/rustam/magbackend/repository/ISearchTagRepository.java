package com.rustam.magbackend.repository;

import com.rustam.magbackend.model.SearchTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISearchTagRepository extends JpaRepository<SearchTag, Long> {

    List<SearchTag> findTopByNameTagContains(String nameTagLike, Pageable p);

    Optional<SearchTag> findSearchTagByNameTag(String nameTag);
}
