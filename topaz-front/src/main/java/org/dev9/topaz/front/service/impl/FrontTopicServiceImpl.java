package org.dev9.topaz.front.service.impl;

import org.dev9.topaz.common.dao.repository.TopicRepository;
import org.dev9.topaz.common.entity.Topic;
import org.dev9.topaz.front.service.FrontTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("FrontTopicService")
public class FrontTopicServiceImpl implements FrontTopicService {
    @Resource
    private TopicRepository topicRepository;

    @Override
    public Page<Topic> findAllTopicPages(Integer page, Integer limit) {
        return topicRepository.findAll(
                PageRequest.of(page, limit, Sort.by("postTime"))
        );
    }
}
