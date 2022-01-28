package com.rustam.magbackend.service.data;

import com.rustam.magbackend.dto.data.HistoryDTO;
import com.rustam.magbackend.enums.DataConflictType;
import com.rustam.magbackend.exception.DataServiceException;
import com.rustam.magbackend.model.History;
import com.rustam.magbackend.repository.IHistoryRepository;
import com.rustam.magbackend.utils.converter.HistoryConverter;
import com.rustam.magbackend.utils.converter.IModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class HistoryService {
    IHistoryRepository repository;
    IModelConverter<History, HistoryDTO> historyConverter;

    @Autowired
    public HistoryService(IHistoryRepository repository) {
        this.repository = repository;
        historyConverter = new HistoryConverter();
    }

    @PreAuthorize("hasAuthority('P_USER_RD') and hasAuthority('P_PUBLICATION_RD')")
    public Page<HistoryDTO> getPageViewUserHistory(Integer pageNum, Integer pageSize, String sortField, String sortOrder){
        Page<History> page = repository.findAllUserHistoryPage(PageRequest.of(
                pageNum,
                pageSize,
                Sort.by((sortOrder.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC), sortField)
        ));
        return historyConverter.toPageDTO(page);
    }

    @PreAuthorize("hasAuthority('P_USER_RD') and hasAuthority('P_PUBLICATION_RD')")
    public Page<HistoryDTO> getPageViewStaffHistory(Integer pageNum, Integer pageSize, String sortField, String sortOrder){
        Page<History> page = repository.findAllStaffHistoryPage(PageRequest.of(
                pageNum,
                pageSize,
                Sort.by((sortOrder.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC), sortField)
        ));
        return historyConverter.toPageDTO(page);
    }

    @PreAuthorize("hasAuthority('P_USER_RD') and hasAuthority('P_PUBLICATION_RD')")
    public List<HistoryDTO> getKeyListHistory(    Long idAccount,
                                                  Long idPublication,
                                                  String dateAfterSearch,
                                                  Integer limit) throws DataServiceException {
        try{
            Timestamp ts = Timestamp.valueOf(dateAfterSearch+ " 00:00:00");
            List<History> list = repository.findTopByAccount_IdAndPublication_IdAndDatetimeIssueAfter(
                    idAccount,
                    idPublication,
                    ts,
                    PageRequest.of(0,limit)
                    );
            return historyConverter.toListKeyDTO(list);
        } catch (IllegalArgumentException ex){
            throw new DataServiceException(DataConflictType.ARG_INVALID, dateAfterSearch + " is not valid date type");
        }
    }
}
