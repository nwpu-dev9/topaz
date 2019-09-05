package org.dev9.topaz.runner;

import org.dev9.topaz.api.TopazApiApplication;
import org.dev9.topaz.back.TopazBackApplication;
import org.dev9.topaz.common.TopazCommonApplication;
import org.dev9.topaz.common.dao.repository.CommentRepository;
import org.dev9.topaz.common.dao.repository.MessageRepository;
import org.dev9.topaz.common.dao.repository.TopicRepository;
import org.dev9.topaz.common.dao.repository.UserRepository;
import org.dev9.topaz.common.entity.Message;
import org.dev9.topaz.front.TopazFrontApplication;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@EnableRabbit
@SpringBootApplication
public class TopazRunnerApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(new Class[]{
                TopazCommonApplication.class,
                TopazApiApplication.class,
                TopazBackApplication.class,
                TopazFrontApplication.class}, args);
        new StartupListener(ctx.getBean(TopicRepository.class), ctx.getBean(CommentRepository.class),
                ctx.getBean(UserRepository.class), ctx.getBean(MessageRepository.class), ctx.getBean(AmqpAdmin.class)).run();
    }
}
