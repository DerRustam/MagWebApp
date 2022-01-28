package com.rustam.magbackend.repository;

import com.rustam.magbackend.model.MatureRating;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMatureRatingRepository extends JpaRepository<MatureRating, Short> {
    List<MatureRating> findTopByNameRatingContains(String nameRatingLike, Pageable p);

    Optional<MatureRating> findMatureRatingByNameRating(String nameRating);

    //Page<AgeRating> findAll(Pageable pageable);
}
