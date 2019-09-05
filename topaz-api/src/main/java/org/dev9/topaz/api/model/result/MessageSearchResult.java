package org.dev9.topaz.api.model.result;

import java.time.Instant;

public interface MessageSearchResult {
    Integer getMessageId();
    String getContent();
    Instant getSentTime();
    Boolean getIsLooked();
    UserSearchResult getSender();
    UserSearchResult getReceiver();
}
