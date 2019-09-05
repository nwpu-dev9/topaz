package org.dev9.topaz.runner;

import org.dev9.topaz.common.dao.repository.CommentRepository;
import org.dev9.topaz.common.dao.repository.TopicRepository;
import org.dev9.topaz.common.dao.repository.UserRepository;
import org.dev9.topaz.common.entity.Comment;
import org.dev9.topaz.common.entity.Topic;
import org.dev9.topaz.common.entity.User;
import org.dev9.topaz.common.util.SensitiveWordUtil;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public class StartupListener {
    public StartupListener(TopicRepository topicRepository, CommentRepository commentRepository, UserRepository userRepository,
                           AmqpAdmin amqpAdmin) {
        this.topicRepository = topicRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.amqpAdmin=amqpAdmin;
    }

    private TopicRepository topicRepository;
    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private AmqpAdmin amqpAdmin;

    public void run() {
        initUtils();
        initRabbitMQ();

        Random random = new Random();
        User user1 = new User("张三", null, "passwd", null, false);
        User user2 = new User("李四", null, "passwd", null, true);
        User user3 = new User("王五", null, "passwd", null, false);
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

    private void initUtils(){
        try {
            SensitiveWordUtil.initWords();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // System.out.println(SensitiveWordUtil.filter("钦定接班人"));
    }

    private void initRabbitMQ(){
        amqpAdmin.declareExchange(new DirectExchange("topaz.exchange"));
        amqpAdmin.declareQueue(new Queue("topaz.queue", true));
        amqpAdmin.declareBinding(new Binding("topaz.queue", Binding.DestinationType.QUEUE, "topaz.exchange", "topaz.route", null));
    }
}
