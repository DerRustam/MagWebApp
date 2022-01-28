package com.rustam.magbackend.service.data;

import com.rustam.magbackend.dto.data.*;
import com.rustam.magbackend.enums.DataConflictType;
import com.rustam.magbackend.enums.ImageTypeSize;
import com.rustam.magbackend.exception.DataServiceException;
import com.rustam.magbackend.model.*;
import com.rustam.magbackend.repository.*;
import com.rustam.magbackend.utils.compression.ImageCompressionUtil;
import com.rustam.magbackend.utils.compression.PreparedImage;
import com.rustam.magbackend.utils.converter.MatureRatingConverter;
import com.rustam.magbackend.utils.converter.PublicationConverter;
import com.rustam.magbackend.utils.converter.SearchTagConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class PublicationService {
    IPublicationBaseRepository<Publication> publicationRepository;
    IMatureRatingRepository matureRatingRepository;
    ISearchTagRepository searchTagRepository;
    IPictureImageRepository imageRepository;

    PublicationConverter publicationConverter;
    MatureRatingConverter matureRatingConverter;
    SearchTagConverter searchTagConverter;

    private final ImageCompressionUtil compressionUtil;

    @Autowired
    public PublicationService(IPublicationBaseRepository<Publication> publicationRepository,
                              IMatureRatingRepository matureRatingRepository,
                              ISearchTagRepository searchTagRepository,
                              IPictureImageRepository imageRepository) {
        this.publicationRepository = publicationRepository;
        this.matureRatingRepository = matureRatingRepository;
        this.searchTagRepository = searchTagRepository;
        this.imageRepository = imageRepository;
        publicationConverter = new PublicationConverter();
        matureRatingConverter = new MatureRatingConverter();
        searchTagConverter = new SearchTagConverter();
        compressionUtil = new ImageCompressionUtil();
    }

    @PreAuthorize("hasAuthority('P_PUBLICATION_RD')")
    public Page<PublicationDTO> getPageViewPublication(int pageNum, int pageSize, String sortField, String sortOrder) {
        Page<Publication> page = publicationRepository.findAll(PageRequest.of(
                pageNum,
                pageSize,
                Sort.by((sortOrder.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC), sortField)
        ));
        return publicationConverter.toPageDTO(page);
    }

    @PreAuthorize("hasAuthority('P_PUBLICATION_RD')")
    public List<PublicationDTO> getKeyListPublication(Long idAlbum, String namePublicationSearch, Integer limit){
        List<Publication> list = publicationRepository.findPublicationsByAlbum_IdAndNamePublication(
                idAlbum,
                namePublicationSearch,
                PageRequest.of(0, limit));
        return publicationConverter.toListKeyDTO(list);
    }

    @PreAuthorize("hasAuthority('P_PUBLICATION_WR')")
    public PublicationDTO addPublication(PublicationDTO dto, MultipartFile file){
        if (dto instanceof PicturePublicationDTO){
            return addPicturePublication((PicturePublicationDTO) dto, file);
        }
        else if (dto instanceof NotePublicationDTO){
            return addNotePublication((NotePublicationDTO) dto);
        }
        throw new DataServiceException(DataConflictType.UNKNOWN, dto.toString());
    }

    @Transactional
    protected PublicationDTO addPicturePublication(PicturePublicationDTO dto, MultipartFile file){
        List<SearchTagDTO> dtoList = sortFilterSearchTags(dto.getSearchTags());
        List<SearchTag> tags = loadSearchTags(dtoList);
        MatureRating rating = loadMatureRating(dto.getMatureRating());
        Album album = loadAlbum(dto.getAlbum());
        String type = getImageContentType(file);
        Picture p = new Picture(
                album,
                dto.getNamePublication(),
                rating,
                dto.getIsPublic(),
                dto.getIsLocked(),
                dto.getDatePublication(),
                tags,
                type,
                dto.getDescription()
        );
        p = publicationRepository.save(p);
        PreparedImage pi = getCompressedImageFromFile(file);
        PictureImage pictureImage = new PictureImage(p.getId(), ImageTypeSize.THUMBNAIL, pi.getThumb());
        imageRepository.save(pictureImage);
        if (pi.getView() != null){
            pictureImage = new PictureImage(p.getId(), ImageTypeSize.VIEW, pi.getView());
            imageRepository.save(pictureImage);
        }
        if (pi.getFull() != null){
            pictureImage = new PictureImage(p.getId(), ImageTypeSize.FULL, pi.getFull());
            imageRepository.save(pictureImage);
        }
        return publicationConverter.toDTO(p);
    }

    @Transactional
    protected PublicationDTO addNotePublication(NotePublicationDTO dto){
        List<SearchTagDTO> dtoList = sortFilterSearchTags(dto.getSearchTags());
        List<SearchTag> tags = loadSearchTags(dtoList);
        MatureRating rating = loadMatureRating(dto.getMatureRating());
        Album album = loadAlbum(dto.getAlbum());
        Note n = new Note(
                album,
                dto.getNamePublication(),
                rating,
                dto.getIsPublic(),
                dto.getIsLocked(),
                dto.getDatePublication(),
                tags,
                dto.getTextNote()
        );
        n = publicationRepository.save(n);
        return publicationConverter.toDTO(n);
    }

    @PreAuthorize("hasAuthority('P_PUBLICATION_WR')")
    public PublicationDTO updatePublication(PublicationDTO dto, MultipartFile file){
        if (dto instanceof PicturePublicationDTO){
            //return updateToPicturePublication((PicturePublicationDTO) dto, file);
        }
        else if (dto instanceof NotePublicationDTO){
            //return updateToNotePublication((NotePublicationDTO) dto);
        }
        throw new DataServiceException(DataConflictType.UNKNOWN, dto.toString());
    }


    @PreAuthorize("hasAuthority('P_PUBLICATION_WR')")
    public void deletePublication(Long id){
        publicationRepository.deleteById(id);
        imageRepository.deleteAllById(id);
    }

    @PreAuthorize("hasAuthority('P_PUBLICATION_RD')")
    public Page<MatureRatingDTO> getPageViewMatureRating(int pageNum, int pageSize, String sortOrder){
        Page<MatureRating> page = matureRatingRepository.findAll(PageRequest.of(
                pageNum,
                pageSize,
                Sort.by((sortOrder.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC), "nameRating")));
        return matureRatingConverter.toPageDTO(page);
    }

    @PreAuthorize("hasAuthority('P_PUBLICATION_RD')")
    public List<MatureRatingDTO> getKeyListMatureRating(String ratingNameSearch, Integer limit){
        List<MatureRating> list = matureRatingRepository.findTopByNameRatingContains(ratingNameSearch,
                PageRequest.of(0, limit)
        );
        return matureRatingConverter.toListKeyDTO(list);
    }

    @PreAuthorize("hasAuthority('P_PUBLICATION_RD')")
    public Page<SearchTagDTO> getPageViewSearchTag(int pageNum, int pageSize, String sortOrder) {
        Page<SearchTag> page = searchTagRepository.findAll(PageRequest.of(
                pageNum,
                pageSize,
                Sort.by((sortOrder.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC), "nameTag")));
        return searchTagConverter.toPageDTO(page);
    }

    @PreAuthorize("hasAuthority('P_PUBLICATION_RD')")
    public List<SearchTagDTO> getKeyListSearchTag(String tagNameSearch, Integer limit){
        List<SearchTag> list = searchTagRepository.findTopByNameTagContains(tagNameSearch,
                PageRequest.of(0, limit));
        return searchTagConverter.toListKeyDTO(list);
    }

    private List<SearchTagDTO> sortFilterSearchTags(List<SearchTagDTO> dtoTags){
        if (dtoTags == null){
            return new ArrayList<>();
        }
        dtoTags.sort(Comparator.comparing(SearchTagDTO::getNameTag));
        Set<String> names = new HashSet<>();
        for (SearchTagDTO sDto : dtoTags){
            names.add(sDto.getNameTag());
        }
        if (names.size() != dtoTags.size()){
            List<SearchTagDTO> list = new ArrayList<>();
            for (SearchTagDTO sDto : dtoTags){
                if (names.contains(sDto.getNameTag())){
                    list.add(sDto);
                    names.remove(sDto.getNameTag());
                }
            }
            return list;
        }
        return dtoTags;
    }

    private List<SearchTag> loadSearchTags(List<SearchTagDTO> dtoTags) throws DataServiceException {
        List<SearchTag> list = new ArrayList<>();
        for (SearchTagDTO sDto : dtoTags){
            SearchTag tag = new SearchTag();
            if (sDto.getId() != null){
                tag.setId(sDto.getId());
                list.add(tag);
            } else if (sDto.getNameTag() != null){
                tag.setNameTag(sDto.getNameTag());
            }
            else {
                throw new DataServiceException(DataConflictType.NOT_FOUND, sDto.toString());
            }
        }
        return list;
    }

    private MatureRating loadMatureRating(MatureRatingDTO dto) throws DataServiceException{
        MatureRating s = new MatureRating();
        if (dto.getId() != null){
            s.setId(dto.getId());
        } else if (dto.getNameRating() != null) {
            s.setNameRating(dto.getNameRating());
        } else {
            throw new DataServiceException(DataConflictType.NOT_FOUND, dto.toString());
        }
        return s;
    }

    private Album loadAlbum(AlbumDTO dto) throws DataServiceException{
        Album a = new Album();
        if (dto.getId() != null){
            a.setId(dto.getId());
        } else if (dto.getNameAlbum() != null && dto.getUserAccount().getViewNickname() != null) {
            a.setNameAlbum(dto.getNameAlbum());
            UserAccount ua = new UserAccount();
            ua.setViewNickname(dto.getUserAccount().getViewNickname());
            a.setUserAccount(ua);
        } else {
            throw new DataServiceException(DataConflictType.NOT_FOUND, dto.toString());
        }
        return a;
    }

    private String getImageContentType(MultipartFile file) throws DataServiceException{
        if (file.getContentType() == null){
            throw new DataServiceException(DataConflictType.ARG_INVALID, "File has null content type");
        }
        switch (file.getContentType()) {
            case "image/jpeg": {
                return "jpg";
            }
            case "image/png": {
                return "png";
            }
            default: {
                throw new DataServiceException(DataConflictType.ARG_INVALID, "File has incorrect content type");
            }
        }
    }

    private PreparedImage getCompressedImageFromFile(MultipartFile file) throws DataServiceException{
        if (file == null){
            throw new DataServiceException(DataConflictType.ARG_INVALID, "Image file must be defined");
        }
        try{
            return compressionUtil.prepareImage(file);
        } catch (IOException ex){
            throw  new DataServiceException(DataConflictType.ARG_INVALID, ex.getMessage());
        }
    }

    private boolean isTagsChanged(List<SearchTag> tags, List<SearchTagDTO> dtoTags){
        if (tags.size() != dtoTags.size()){
            return true;
        } else {
            for (int i = 0; i< tags.size(); i++){
                if (!tags.get(i).getNameTag().equals(dtoTags.get(i).getNameTag())){
                    return true;
                }
            }
        }
        return false;
    }
}

