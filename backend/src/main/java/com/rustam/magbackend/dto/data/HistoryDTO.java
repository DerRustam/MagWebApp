package com.rustam.magbackend.dto.data;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class HistoryDTO {
    private Long id;
    private AccountDTO account;
    private PublicationDTO publication;
    private Timestamp datetimeIssue;
    private String descriptionHistory;

    public HistoryDTO(Long id, AccountDTO account, PublicationDTO publication, Timestamp datetimeIssue) {
        this.id = id;
        this.account = account;
        this.publication = publication;
        this.datetimeIssue = datetimeIssue;
    }
}
