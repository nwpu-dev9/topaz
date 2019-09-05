package org.dev9.topaz.api.model.result;

import java.time.Instant;

public interface CommentSearchResult {
    Integer getCommentId();
    String getContent();
    Instant getCommentTime();
    UserSearchResult getCommenter();
    TopicSearchResult getTopic();
}
