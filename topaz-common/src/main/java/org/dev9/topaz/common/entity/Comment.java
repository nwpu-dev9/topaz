package org.dev9.topaz.common.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "COMMENT")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @Column
    private String content;

    @Column
    private Date commentTime;

    @Column
    private Integer commentUserId;

    @Column
    private Integer topicId;

    public Comment() {}

    public Comment(String content, Date commentTime, Integer commentUserId, Integer topicId) {
        this.content = content;
        this.commentTime = commentTime;
        this.commentUserId = commentUserId;
        this.topicId = topicId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", content='" + content + '\'' +
                ", commentTime=" + commentTime +
                ", commentUserId=" + commentUserId +
                ", topicId=" + topicId +
                '}';
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Integer getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(Integer commentUserId) {
        this.commentUserId = commentUserId;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }
}
