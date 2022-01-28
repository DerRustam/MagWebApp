package com.rustam.magbackend.dto.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class MatureRatingDTO {
    private Short id;
    @NotEmpty
    @Size(min = 1, max = 5)
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", flags = Pattern.Flag.UNICODE_CASE)
    private String nameRating;
    private String descriptionRating;

    public MatureRatingDTO(Short id, String nameRating) {
        this.id = id;
        this.nameRating = nameRating;
    }
}
