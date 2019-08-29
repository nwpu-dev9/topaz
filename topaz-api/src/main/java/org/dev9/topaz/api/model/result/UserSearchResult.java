package org.dev9.topaz.api.model.result;

import java.time.Instant;

public interface UserSearchResult {
    Integer getUserId();
    String getPhoneNumber();
    String getName();
    Instant getSignupTime();
    String getProfile();
    String getAvatarUrl();
}
