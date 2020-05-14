package com.vikar.work.models;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Nullable
    @ManyToOne(cascade = CascadeType.ALL)
    private Worker senderWorker;

    @Nullable
    @ManyToOne(cascade = CascadeType.ALL)
    private Company senderCompany;

    @Nullable
    @ManyToOne(cascade = CascadeType.ALL)
    private Worker recipientWorker;

    @Nullable
    @ManyToOne(cascade = CascadeType.ALL)
    private Company recipientCompany;

    private String content;
    private String headline;

    @CreationTimestamp
    @DateTimeFormat(pattern="dd-MM-yyyy hh:mm")
    private Date time;

    private Boolean read = false;

    private Boolean deleted = false;

    public Message() {
    }

    @Nullable
    public Worker getSenderWorker() {
        return senderWorker;
    }

    public void setSenderWorker(@Nullable Worker senderWorker) {
        this.senderWorker = senderWorker;
    }

    @Nullable
    public Company getSenderCompany() {
        return senderCompany;
    }

    public void setSenderCompany(@Nullable Company senderCompany) {
        this.senderCompany = senderCompany;
    }

    @Nullable
    public Worker getRecipientWorker() {
        return recipientWorker;
    }

    public void setRecipientWorker(@Nullable Worker recipientWorker) {
        this.recipientWorker = recipientWorker;
    }

    @Nullable
    public Company getRecipientCompany() {
        return recipientCompany;
    }

    public void setRecipientCompany(@Nullable Company recipientCompany) {
        this.recipientCompany = recipientCompany;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }
}
