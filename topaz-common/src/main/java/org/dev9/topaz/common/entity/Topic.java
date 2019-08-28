package org.dev9.topaz.common.entity;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

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
}
