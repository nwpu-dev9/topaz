package org.dev9.topaz.common.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TOPIC")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer topicId;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private Date postTime;

    @Column
    private Integer posterUserId;

    @Column
    private Integer favoriteCount;

    @Column
    private Integer visitedCount;

    public Topic(){}

    public Topic(String title, String content, Date postTime, Integer posterUserId, Integer favoriteCount, Integer visitedCount) {
        this.title = title;
        this.content = content;
        this.postTime = postTime;
        this.posterUserId = posterUserId;
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
                ", posterUserId=" + posterUserId +
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

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public Integer getPosterUserId() {
        return posterUserId;
    }

    public void setPosterUserId(Integer posterUserId) {
        this.posterUserId = posterUserId;
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
