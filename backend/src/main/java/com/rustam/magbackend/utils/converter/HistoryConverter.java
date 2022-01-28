package com.rustam.magbackend.utils.converter;

import com.rustam.magbackend.dto.data.*;
import com.rustam.magbackend.model.*;

import java.util.ArrayList;
import java.util.List;

public class HistoryConverter implements IModelConverter<History, HistoryDTO> {
    @Override
    public HistoryDTO toDTO(History history) {
        AccountDTO accountDTO = getAccountDTO(history);
        PublicationDTO publicationDTO = getPublicationDTO(history);
        return new HistoryDTO(history.getId(), accountDTO, publicationDTO, history.getDatetimeIssue(), history.getDescriptionHistory());
    }

    @Override
    public HistoryDTO toKeyDTO(History history) {
        AccountDTO accountDTO = getAccountKeyDTO(history);
        PublicationDTO publicationDTO = getPublicationKeyDTO(history);
        return new HistoryDTO(history.getId(), accountDTO, publicationDTO, history.getDatetimeIssue());
    }

    private AccountDTO getAccountDTO(History h){
        List<RoleDTO> roles = new ArrayList<>();
        for (Role r : h.getAccount().getRoles()){
            roles.add(new RoleDTO(r.getId(), r.getNameRole()));
        }
        if (h.getAccount() instanceof UserAccount){
            UserAccount ua = (UserAccount)h.getAccount();
            return new UserAccountDTO(ua.getId(), ua.getLocked(), roles, ua.getViewNickname(), ua.getFirstName(), ua.getLastName(), ua.getDateBirth());
        } else if (h.getAccount() instanceof StaffAccount) {
            StaffAccount sa = (StaffAccount)h.getAccount();
            return new StaffAccountDTO(sa.getId(), sa.getLocked(), roles, sa.getStaffMemberUid(), sa.getContactEmail(), sa.getFullName());
        }
        return null;
    }

    private AccountDTO getAccountKeyDTO(History h){
        if (h.getAccount() instanceof UserAccount){
            UserAccount ua = (UserAccount)h.getAccount();
            return new UserAccountDTO(ua.getId(), ua.getLocked(), ua.getViewNickname(), ua.getFirstName(), ua.getLastName(), ua.getDateBirth());
        } else if (h.getAccount() instanceof StaffAccount) {
            StaffAccount sa = (StaffAccount)h.getAccount();
            return new StaffAccountDTO(sa.getId(), sa.getLocked(), sa.getStaffMemberUid(), sa.getContactEmail(), sa.getFullName());
        }
        return null;
    }

    private PublicationDTO getPublicationDTO(History h){
        if (h.getPublication() != null){
            if (h.getPublication() instanceof Picture){
                Picture p = (Picture) h.getPublication();
                return new PicturePublicationDTO(
                        p.getId(),
                        p.getNamePublication(),
                        new MatureRatingDTO(p.getMatureRating().getId(), p.getMatureRating().getNameRating()),
                        p.getPublic(),
                        p.getLocked(),
                        p.getDatePublication(),
                        p.getDescription()
                );
            } else if (h.getPublication() instanceof Note){
                Note n = (Note) h.getPublication();
                return new NotePublicationDTO(
                        n.getId(),
                        n.getNamePublication(),
                        new MatureRatingDTO(n.getMatureRating().getId(), n.getMatureRating().getNameRating()),
                        n.getPublic(),
                        n.getLocked(),
                        n.getDatePublication(),
                        (n.getTextNote().length() > 25) ? n.getTextNote().substring(0, 25) : n.getTextNote()
                );
            }
        }
        return null;
    }

    private PublicationDTO getPublicationKeyDTO(History h){
        if (h.getPublication() != null){
            if (h.getPublication() instanceof Picture){
                Picture p = (Picture) h.getPublication();
                return new PicturePublicationDTO(
                        p.getId(),
                        p.getNamePublication(),
                        new MatureRatingDTO(p.getMatureRating().getId(), p.getMatureRating().getNameRating()),
                        p.getPublic(),
                        p.getLocked(),
                        p.getDatePublication()
                );
            } else if (h.getPublication() instanceof Note){
                Note n = (Note) h.getPublication();
                return new NotePublicationDTO(
                        n.getId(),
                        n.getNamePublication(),
                        new MatureRatingDTO(n.getMatureRating().getId(), n.getMatureRating().getNameRating()),
                        n.getPublic(),
                        n.getLocked(),
                        n.getDatePublication()
                );
            }
        }
        return null;
    }
}
