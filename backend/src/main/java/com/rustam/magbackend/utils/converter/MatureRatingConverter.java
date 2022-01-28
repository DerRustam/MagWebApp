package com.rustam.magbackend.utils.converter;

import com.rustam.magbackend.dto.data.MatureRatingDTO;
import com.rustam.magbackend.model.MatureRating;

public class MatureRatingConverter implements IModelConverter<MatureRating, MatureRatingDTO>{
    @Override
    public MatureRatingDTO toDTO(MatureRating matureRating) {
        return new MatureRatingDTO(matureRating.getId(), matureRating.getNameRating(), matureRating.getDescriptionRating());
    }

    @Override
    public MatureRatingDTO toKeyDTO(MatureRating matureRating) {
        return new MatureRatingDTO(matureRating.getId(), matureRating.getNameRating());
    }
}
