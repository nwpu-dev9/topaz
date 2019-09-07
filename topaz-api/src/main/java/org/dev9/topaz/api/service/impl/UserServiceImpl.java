package org.dev9.topaz.api.service.impl;

import org.dev9.topaz.api.service.UserService;
import org.dev9.topaz.common.dao.repository.UserRepository;
import org.dev9.topaz.common.entity.Topic;
import org.dev9.topaz.common.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service("ApiUserService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;

    @Override
    public void updateUserInformation(User user) {
        userRepository.save(user);
    }

    @Override
    public void addFavoriteTopic(User user, Topic topic) {
        user.getFavoriteTopics().add(topic);
        userRepository.save(user);
    }

    @Override
    public void deleteFavoriteTopic(User user, Topic topic) {
        user.getFavoriteTopics().remove(topic);
        userRepository.save(user);
        userRepository.flush();
    }
}
