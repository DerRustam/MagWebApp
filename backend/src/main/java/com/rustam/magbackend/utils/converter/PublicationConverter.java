package com.rustam.magbackend.utils.converter;

import com.rustam.magbackend.dto.data.*;
import com.rustam.magbackend.model.*;

import java.util.ArrayList;
import java.util.List;

public class PublicationConverter implements IModelConverter<Publication, PublicationDTO>{

    @Override
    public PublicationDTO toDTO(Publication publication) {
        AlbumDTO albumDTO = getAlbumDTO(publication);
        List<SearchTagDTO> tags= new ArrayList<>();
        for (SearchTag s : publication.getSearchTags()){
            tags.add(new SearchTagDTO(s.getId(), s.getNameTag()));
        }
        MatureRatingDTO matureRatingDTO = null;
        if (publication.getMatureRating() != null){
            matureRatingDTO = new MatureRatingDTO(publication.getMatureRating().getId(), publication.getMatureRating().getNameRating());
        }
        if (publication instanceof Picture){
            Picture p = (Picture) publication;
            return new PicturePublicationDTO(
                    p.getId(),
                    albumDTO,
                    p.getNamePublication(),
                    matureRatingDTO,
                    p.getPublic(),
                    p.getLocked(),
                    p.getDatePublication(),
                    tags,
                    p.getDescription(),
                    p.getExtension()
            );
        } else {
            Note n = (Note)publication;
            return new NotePublicationDTO(
                    n.getId(),
                    albumDTO,
                    n.getNamePublication(),
                    matureRatingDTO,
                    n.getPublic(),
                    n.getLocked(),
                    n.getDatePublication(),
                    tags,
                    (n.getTextNote().length() > 25) ? n.getTextNote().substring(0, 25) : n.getTextNote()
            );
        }
    }

    @Override
    public PublicationDTO toKeyDTO(Publication publication) {
        AlbumDTO albumDTO = getAlbumDTO(publication);
        if (publication instanceof Picture){
            Picture p = (Picture) publication;
            return new PicturePublicationDTO(
                    p.getId(),
                    albumDTO,
                    p.getNamePublication()
            );
        } else {
            Note n = (Note)publication;
            return new NotePublicationDTO(
                    n.getId(),
                    albumDTO,
                    n.getNamePublication()
            );
        }
    }

    private AlbumDTO getAlbumDTO(Publication publication){
        List<RoleDTO> roles = new ArrayList<>();
        for (Role r : publication.getAlbum().getUserAccount().getRoles()){
            roles.add(new RoleDTO(r.getId(), r.getNameRole()));
        }
        UserAccountDTO accountDTO = new UserAccountDTO(
                publication.getAlbum().getUserAccount().getId(),
                publication.getAlbum().getUserAccount().getLocked(),
                roles,
                publication.getAlbum().getUserAccount().getViewNickname()
        );
        return new AlbumDTO(
                publication.getAlbum().getId(),
                accountDTO,
                publication.getAlbum().getNameAlbum()
        );
    }
}
