package org.dev9.topaz.api.service.impl;
import org.dev9.topaz.api.service.TopicService;
import org.dev9.topaz.common.dao.repository.TopicRepository;
import org.dev9.topaz.common.entity.Topic;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("ApiTopicService")
public class TopicServiceImpl implements TopicService {
    @Resource
    private TopicRepository topicRepository;

    @Override
    public Topic saveTopic(Topic topic) {
        return topicRepository.save(topic);
    }

}


