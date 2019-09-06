package org.dev9.topaz.api.model.result.impl;

import org.dev9.topaz.api.model.result.TopicSearchResult;
import org.dev9.topaz.api.model.result.UserSearchResult;
import org.dev9.topaz.common.entity.Topic;

import java.time.Instant;

public class TopicSearchResultImpl implements TopicSearchResult {
    private Integer topicId;
    private String title;
    private String content;
    private Instant postTime;
    private UserSearchResult poster;
    private Integer favoriteCount;
    private Integer visitedCount;
    private Boolean audited;

    public TopicSearchResultImpl(Topic topic){
        this.topicId = topic.getTopicId();
        this.title = topic.getTitle();
        this.content = topic.getContent();
        this.postTime = topic.getPostTime();
        this.poster = new UserSearchResultImpl(topic.getPoster()); // null;// topic.getPoster();
        this.favoriteCount = topic.getFavoriteCount();
        this.visitedCount = topic.getVisitedCount();
        this.audited = topic.getAudited();
    }

    public TopicSearchResultImpl(Integer topicId, String title, String content, Instant postTime, UserSearchResult poster, Integer favoriteCount, Integer visitedCount, Boolean audited) {
        this.topicId = topicId;
        this.title = title;
        this.content = content;
        this.postTime = postTime;
        this.poster = poster;
        this.favoriteCount = favoriteCount;
        this.visitedCount = visitedCount;
        this.audited = audited;
    }

    @Override
    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public Instant getPostTime() {
        return postTime;
    }

    public void setPostTime(Instant postTime) {
        this.postTime = postTime;
    }

    @Override
    public UserSearchResult getPoster() {
        return poster;
    }

    public void setPoster(UserSearchResult poster) {
        this.poster = poster;
    }

    @Override
    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    @Override
    public Integer getVisitedCount() {
        return visitedCount;
    }

    public void setVisitedCount(Integer visitedCount) {
        this.visitedCount = visitedCount;
    }

    @Override
    public Boolean getAudited() {
        return audited;
    }

    public void setAudited(Boolean audited) {
        this.audited = audited;
    }
}