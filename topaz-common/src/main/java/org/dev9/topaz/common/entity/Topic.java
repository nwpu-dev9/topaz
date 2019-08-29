package org.dev9.topaz.common.entity;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TOPIC")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer topicId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private Instant postTime;

    @ManyToOne
    @JoinColumn(name = "poster_uid", nullable = false)
    private User poster;

    @Column(nullable = false)
    private Integer favoriteCount;

    @Column(nullable = false)
    private Integer visitedCount;

    @OneToMany(mappedBy = "topic")
    private List<Comment> comments = new ArrayList<>();

    public Topic() {
    }

    public Topic(String title, String content, Instant postTime, User poster, Integer favoriteCount, Integer visitedCount) {
        this.title = title;
        this.content = content;
        this.postTime = postTime;
        this.poster = poster;
        this.favoriteCount = favoriteCount;
        this.visitedCount = visitedCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return topicId.equals(topic.topicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topicId);
    }

    @Override
    public String toString() {
        return "Topic{" +
                "topicId=" + topicId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", postTime=" + postTime +
                ", poster=" + poster +
                ", favoriteCount=" + favoriteCount +
                ", visitedCount=" + visitedCount +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getPostTime() {
        return postTime;
    }

    public void setPostTime(Instant postTime) {
        this.postTime = postTime;
    }

    public User getPoster() {
        return poster;
    }

    public void setPoster(User poster) {
        this.poster = poster;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public Integer getVisitedCount() {
        return visitedCount;
    }

    public void setVisitedCount(Integer visitedCount) {
        this.visitedCount = visitedCount;
    }

    public void addComment(Comment comment) {
        comment.setTopic(this);
        comments.add(comment);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setTopic(null);
    }

    public List<Comment> getComments() {
        return comments;
    }
}
