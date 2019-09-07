package org.dev9.topaz.common.configuration;

public class RabbitMqConfig {
    public static final Integer TOTAL_COUNT=3;
    private static Integer counter=0;

    public static synchronized String getRouteKey(){
        return "topaz.route"+((counter++)%3);
    }
}
