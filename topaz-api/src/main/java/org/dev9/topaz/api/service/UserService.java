package org.dev9.topaz.api.service;

import org.dev9.topaz.common.entity.Topic;
import org.dev9.topaz.common.entity.User;

public interface UserService {
    void updateUserInformation(User user);
    void addFavoriteTopic(User user, Topic topic);
}
