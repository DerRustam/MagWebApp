package com.rustam.magbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(
        name = "history",
        uniqueConstraints = {@UniqueConstraint(columnNames ={"id_account", "id_publication", "datetime_issue"})}
        )
public class History {
    @Id
    @SequenceGenerator(
            name = "history_id_history_seq",
            sequenceName = "history_id_history_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "history_id_history_seq")
    @Column(name = "id_history", updatable = false, insertable = false)
    private Long id;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_account", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Account account;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_publication")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Publication publication;

    @NotNull
    @Column(name = "datetime_issue", nullable = false)
    private Timestamp datetimeIssue;

    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "description_history", length = 300, nullable = false)
    private String descriptionHistory;

    public History() {
    }

    public History(@NotNull Account account, Publication publication, @NotNull Timestamp datetimeIssue, @NotNull @Size(min = 1, max = 300) String descriptionHistory) {
        this.account = account;
        this.publication = publication;
        this.datetimeIssue = datetimeIssue;
        this.descriptionHistory = descriptionHistory;
    }

    public History(Long id, @NotNull Account account, Publication publication, @NotNull Timestamp datetimeIssue, @NotNull @Size(min = 1, max = 300) String descriptionHistory) {
        this.id = id;
        this.account = account;
        this.publication = publication;
        this.datetimeIssue = datetimeIssue;
        this.descriptionHistory = descriptionHistory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public Timestamp getDatetimeIssue() {
        return datetimeIssue;
    }

    public void setDatetimeIssue(Timestamp datetimeIssue) {
        this.datetimeIssue = datetimeIssue;
    }

    public String getDescriptionHistory() {
        return descriptionHistory;
    }

    public void setDescriptionHistory(String descriptionHistory) {
        this.descriptionHistory = descriptionHistory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof History)) return false;
        History history = (History) o;
        return getId().equals(history.getId()) &&
                getAccount().equals(history.getAccount()) &&
                getPublication().equals(history.getPublication()) &&
                getDatetimeIssue().equals(history.getDatetimeIssue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
