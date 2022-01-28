package com.rustam.magbackend.utils.converter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

public interface IModelConverter<Model, DTO> {
    DTO toDTO(Model model);
    DTO toKeyDTO(Model model);

    default Page<DTO> toPageDTO(Page<Model> modelPage){
        List<DTO> dtoList = new ArrayList<>();
        for (Model m : modelPage.getContent()){
            dtoList.add(toDTO(m));
        }
        return new PageImpl<>(dtoList, modelPage.getPageable(), modelPage.getTotalElements());
    }

    default List<DTO> toListKeyDTO(List<Model> modelList){
        List<DTO> dtoList = new ArrayList<>();
        for (Model m : modelList){
            dtoList.add(toKeyDTO(m));
        }
        return dtoList;
    }
}
