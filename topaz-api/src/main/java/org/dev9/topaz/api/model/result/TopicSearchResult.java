package org.dev9.topaz.api.model.result;

import java.time.Instant;

public interface TopicSearchResult {
    Integer getTopicId();
    String getTitle();
    String getContent();
    Instant getPostTime();
    UserSearchResult getPoster();
    Integer getFavoriteCount();
    Integer getVisitedCount();
}
