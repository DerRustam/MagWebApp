package com.rustam.magbackend.utils.converter;


import com.rustam.magbackend.dto.data.SearchTagDTO;
import com.rustam.magbackend.model.SearchTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

public class SearchTagConverter implements IModelConverter<SearchTag, SearchTagDTO>{
    @Override
    public SearchTagDTO toDTO(SearchTag searchTag) {
        return new SearchTagDTO(searchTag.getId(), searchTag.getNameTag());
    }

    @Override
    public SearchTagDTO toKeyDTO(SearchTag searchTag) {
        return new SearchTagDTO(searchTag.getId(), searchTag.getNameTag());
    }
}
