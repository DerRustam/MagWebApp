package com.rustam.magbackend.dto.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
@AllArgsConstructor
public class AlbumDTO {
    private Long id;
    @NotNull
    private UserAccountDTO userAccount;
    @NotEmpty
    @Size(min = 1, max = 40)
    @Pattern(regexp = "^[a-zA-Z0-9_<>@*#!?]+$", flags = Pattern.Flag.UNICODE_CASE)
    private String nameAlbum;
    @NotNull
    private Date dateCreation;
    private String descriptionAlbum;

    public AlbumDTO(Long id, UserAccountDTO userAccount, String nameAlbum) {
        this.id = id;
        this.userAccount = userAccount;
        this.nameAlbum = nameAlbum;
    }
}
