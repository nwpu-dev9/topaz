package org.dev9.topaz.runner;

import org.dev9.topaz.common.dao.repository.CommentRepository;
import org.dev9.topaz.common.dao.repository.TopicRepository;
import org.dev9.topaz.common.dao.repository.UserRepository;
import org.dev9.topaz.common.entity.Comment;
import org.dev9.topaz.common.entity.Topic;
import org.dev9.topaz.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.naming.Context;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;

public class StartupListener {
    public StartupListener(TopicRepository topicRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    private TopicRepository topicRepository;
    private CommentRepository commentRepository;
    private UserRepository userRepository;

    public void run() {
        User user1 = new User(null, "zhangsan", "passwd", Instant.now(), null, null, Collections.emptyList());
        User user2 = new User(null, "lisi", "passwd", Instant.now(), null, null, Collections.emptyList());
        userRepository.save(user1);
        userRepository.save(user2);
        for (int i = 1; i <= 23; i++) {
            Topic topic = new Topic(String.format("test post %s", i), String.format("test content %s", i), Instant.now(), user1, 1, 1);
            topicRepository.save(topic);
            for (int j = 0; j < 10; j++) {
                commentRepository.save(new Comment(String.format("comment content %s", j), Instant.now(), user2, topic));
            }
        }
    }
}