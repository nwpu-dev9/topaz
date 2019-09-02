package org.dev9.topaz.back.controller;

import org.dev9.topaz.common.dao.repository.TopicRepository;
import org.dev9.topaz.common.entity.Topic;
import org.dev9.topaz.common.exception.PageNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller("BackTopicController")
@RequestMapping("/admin/topic")
public class TopicController {

    @Resource
    private TopicRepository topicRepository;

    @GetMapping({"", "/"})
    public String topicIndex(@RequestParam(defaultValue = "0") Integer page,
                             @RequestParam(defaultValue = "20") Integer limit,
                             Map<String, Object> map){
        Page<Topic> topicPage=topicRepository.findAll(PageRequest.of(page, limit, Sort.by("postTime")));

        map.put("topics",topicPage.getContent());
        map.put("page", page);
        map.put("limit", limit);
        map.put("pageCount", topicPage.getTotalPages());

        return "back_topic_index";
    }

    @GetMapping(value = "{topicId}")
    public ModelAndView getTopic(@PathVariable Integer topicId) {
        HashMap<String, Object> params = new HashMap<>();
        Optional<Topic> topic = topicRepository.findById(topicId);
        if (!topic.isPresent()) {
            throw new PageNotFoundException();
        }
        params.put("topic", topic.get());
        return new ModelAndView("back_topic", params);
    }
}
