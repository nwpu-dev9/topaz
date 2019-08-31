package org.dev9.topaz.common.entity;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "MESSAGE")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer messageId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private Instant sentTime;

    @Column(nullable = false)
    private Boolean isLooked;

    @ManyToOne
    @JoinColumn(name = "sender_uid", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_uid", nullable = false)
    private User receiver;

    public Message() {
    }

    public Message(String content, Instant sentTime, Boolean isLooked, User sender, User receiver) {
        this.content = content;
        this.sentTime = sentTime;
        this.isLooked = isLooked;
        this.sender = sender;
        this.receiver = receiver;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getSentTime() {
        return sentTime;
    }

    public void setSentTime(Instant sentTime) {
        this.sentTime = sentTime;
    }

    public Boolean getLooked() {
        return isLooked;
    }

    public void setLooked(Boolean looked) {
        isLooked = looked;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}

