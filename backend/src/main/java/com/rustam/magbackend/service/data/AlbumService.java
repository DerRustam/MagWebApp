package com.rustam.magbackend.service.data;

import com.rustam.magbackend.dto.data.AlbumDTO;
import com.rustam.magbackend.model.Album;
import com.rustam.magbackend.repository.IAlbumRepository;
import com.rustam.magbackend.utils.converter.AlbumConverter;
import com.rustam.magbackend.utils.converter.IModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {
    IAlbumRepository albumRepository;

    IModelConverter<Album, AlbumDTO> albumConverter;

    @Autowired
    public AlbumService(IAlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
        albumConverter = new AlbumConverter();
    }


    @PreAuthorize("hasAuthority('P_USER_RD')")
    public Page<AlbumDTO> getPageViewAlbum(int pageNum, int pageSize, String sortOrder, String sortField){
        Page<Album> page = albumRepository.findAll(PageRequest.of(
                pageNum,
                pageSize,
                Sort.by((sortOrder.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC), sortField)
        ));
        return albumConverter.toPageDTO(page);
    }

    @PreAuthorize("hasAuthority('P_USER_RD')")
    public List<AlbumDTO> getKeyListViewAlbum(Long idAccount, String nameAlbum, Integer limit){
        List<Album> list = albumRepository.findTopByUserAccount_IdAndNameAlbumContains(
                idAccount,
                nameAlbum,
                PageRequest.of(0, limit)
                );
        return albumConverter.toListKeyDTO(list);
    }
}
