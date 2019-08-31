package org.dev9.topaz.api.service;


import org.dev9.topaz.common.entity.Topic;

public interface TopicService {
    void saveTopic(Topic topic);
    void DeleteTopic(Topic topic);
}
