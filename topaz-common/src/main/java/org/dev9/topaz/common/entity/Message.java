package org.dev9.topaz.common.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MESSAGE")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer messageId;

    @Column
    private String content;

    @Column
    private Date sentTime;

    @Column
    private Boolean isLooked;

    @Column
    private Integer sentUserId;

    @Column
    private Integer receiveUserId;

    public Message(){}

    public Message(String content, Date sentTime, Boolean isLooked, Integer sentUserId, Integer receiveUserId) {
        this.content = content;
        this.sentTime = sentTime;
        this.isLooked = isLooked;
        this.sentUserId = sentUserId;
        this.receiveUserId = receiveUserId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", content='" + content + '\'' +
                ", sentTime=" + sentTime +
                ", isLooked=" + isLooked +
                ", sentUserId=" + sentUserId +
                ", receiveUserId=" + receiveUserId +
                '}';
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }

    public Boolean getLooked() {
        return isLooked;
    }

    public void setLooked(Boolean looked) {
        isLooked = looked;
    }

    public Integer getSentUserId() {
        return sentUserId;
    }

    public void setSentUserId(Integer sentUserId) {
        this.sentUserId = sentUserId;
    }

    public Integer getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(Integer receiveUserId) {
        this.receiveUserId = receiveUserId;
    }
}

