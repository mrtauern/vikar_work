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

    @CreationTimestamp
    @DateTimeFormat(pattern="dd-MM-yyyy hh:mm")
    private Date time;

    private Boolean read = false;

    private Boolean deleted = false;
}
