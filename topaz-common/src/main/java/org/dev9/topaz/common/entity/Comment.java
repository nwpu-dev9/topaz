package org.dev9.topaz.common.entity;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "COMMENT")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private Instant commentTime;

    @ManyToOne
    @JoinColumn(name = "commenter_id", nullable = false)
    private User commenter;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;


    public Comment() {
    }

    public Comment(String content, Instant commentTime, User commenter, Topic topic) {
        this.content = content;
        this.commentTime = commentTime;
        this.commenter = commenter;
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", content='" + content + '\'' +
                ", commentTime=" + commentTime +
                ", commenter=" + commenter.getUserId() +
                ", topic=" + topic.getTopicId() +
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

    public Instant getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Instant commentTime) {
        this.commentTime = commentTime;
    }

    public User getCommenter() {
        return commenter;
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
