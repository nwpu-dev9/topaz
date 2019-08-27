package org.dev9.topaz.front.service;

import org.dev9.topaz.common.entity.Topic;
import org.springframework.data.domain.Page;

public interface FrontTopicService {
     Page<Topic> findAllTopicPages(Integer page, Integer limit);
}
