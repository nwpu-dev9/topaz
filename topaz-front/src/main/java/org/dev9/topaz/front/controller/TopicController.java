package org.dev9.topaz.front.controller;

import org.dev9.topaz.common.dao.repository.TopicRepository;
import org.dev9.topaz.common.entity.Topic;
import org.dev9.topaz.common.exception.PageNotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Optional;

@Controller
public class TopicController {
    @Resource
    private TopicRepository topicRepository;

    @GetMapping(value = "/topic/{topicId}")
    public ModelAndView getTopic(@PathVariable Integer topicId) {
        HashMap<String, Object> params = new HashMap<>();
        Optional<Topic> topic = topicRepository.findById(topicId);
        if (!topic.isPresent()) {
            throw new PageNotFoundException();
        }
        params.put("topic", topic.get());
        return new ModelAndView("topic", params);
    }
}
