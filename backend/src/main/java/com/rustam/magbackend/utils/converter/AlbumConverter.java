package com.rustam.magbackend.utils.converter;

import com.rustam.magbackend.dto.data.AccountDTO;
import com.rustam.magbackend.dto.data.AlbumDTO;
import com.rustam.magbackend.dto.data.RoleDTO;
import com.rustam.magbackend.dto.data.UserAccountDTO;
import com.rustam.magbackend.model.Album;
import com.rustam.magbackend.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

public class AlbumConverter implements IModelConverter<Album, AlbumDTO>{

    @Override
    public AlbumDTO toDTO(Album album) {
        UserAccountDTO accountDTO = getUserAccountDTO(album);
        return new AlbumDTO(album.getId(), accountDTO, album.getNameAlbum(), album.getDateCreation(), album.getDescriptionAlbum());
    }

    @Override
    public AlbumDTO toKeyDTO(Album album) {
        UserAccountDTO accountDTO = getUserAccountDTO(album);
        return new AlbumDTO(album.getId(), accountDTO, album.getNameAlbum());
    }

    private UserAccountDTO getUserAccountDTO(Album album){
        List<RoleDTO> roles = new ArrayList<>();
        for (Role r : album.getUserAccount().getRoles()){
            roles.add(new RoleDTO(r.getId(), r.getNameRole()));
        }
        return new UserAccountDTO(
                album.getUserAccount().getId(),
                album.getUserAccount().getLocked(),
                roles,
                album.getUserAccount().getViewNickname()
        );
    }
}
