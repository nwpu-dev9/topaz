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
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

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
        Random random = new Random();
        User user1 = new User("张三", null, "passwd", null);
        User user2 = new User("李四", null, "passwd", null);
        User user3 = new User("王五", null, "passwd", null);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        for (int i = 1; i <= 23; i++) {
            Instant baseTime = Instant.now().minus(Duration.ofDays(7).plus(Duration.ofHours(i * 4)));
            Topic topic = new Topic(
                    String.format("测试标题 %s", i),
                    String.format("<h1>一级标题 %s</h1><p>内容 %s</p><p>内容 %s</p><p>内容 %s</p>", i, i, i, i),
                    baseTime,
                    userRepository.findById(random.nextInt(2) + 1).get(),
                    1,
                    1);
            topicRepository.save(topic);
            for (int j = 0; j < 7 + new Random().nextInt(7); j++) {
                commentRepository.save(new Comment(String.format("评论内容 %s\n评论内容 %s", j, j), baseTime.plus(Duration.ofHours(j * 2)), userRepository.findById(random.nextInt(2) + 1).get(), topic));
            }
        }
    }
}