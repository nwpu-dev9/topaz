package org.dev9.topaz.runner;

import org.dev9.topaz.api.TopazApiApplication;
import org.dev9.topaz.back.TopazBackApplication;
import org.dev9.topaz.common.TopazCommonApplication;
import org.dev9.topaz.front.TopazFrontApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TopazRunnerApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Class[]{
                TopazCommonApplication.class,
                TopazApiApplication.class,
                TopazBackApplication.class,
                TopazFrontApplication.class
        }, args);
    }

}
