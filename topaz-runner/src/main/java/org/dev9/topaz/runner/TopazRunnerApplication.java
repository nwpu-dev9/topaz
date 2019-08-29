package org.dev9.topaz.runner;

import org.dev9.topaz.api.TopazApiApplication;
import org.dev9.topaz.back.TopazBackApplication;
import org.dev9.topaz.common.TopazCommonApplication;
import org.dev9.topaz.common.dao.repository.CommentRepository;
import org.dev9.topaz.common.dao.repository.TopicRepository;
import org.dev9.topaz.common.dao.repository.UserRepository;
import org.dev9.topaz.common.entity.Topic;
import org.dev9.topaz.front.TopazFrontApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Collection;
import java.util.Collections;

@SpringBootApplication
public class TopazRunnerApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(new Class[]{
                TopazCommonApplication.class,
                TopazApiApplication.class,
                TopazBackApplication.class,
                TopazFrontApplication.class}, args);
        new StartupListener(ctx.getBean(TopicRepository.class), ctx.getBean(CommentRepository.class), ctx.getBean(UserRepository.class)).run();
    }
}
