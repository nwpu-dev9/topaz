package org.dev9.topaz.front.controller;

import org.dev9.topaz.common.dao.repository.TopicRepository;
import org.dev9.topaz.common.entity.Topic;
import org.dev9.topaz.common.exception.PageNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class EditingController {
    @Resource
    TopicRepository topicRepository;

    @GetMapping("/posts/new")
    public ModelAndView newPost() {
        Map<String, Object> params = new HashMap<>();
        params.put("post", null);
        return new ModelAndView("edit", params);
    }

    @GetMapping("/posts/edit")
    public ModelAndView editPost(@RequestParam Integer id) {
        Map<String, Object> params = new HashMap<>();
        Optional<Topic> topic = topicRepository.findById(id);
        if (!topic.isPresent()) {
            throw new PageNotFoundException();
        }
        params.put("post", topic.get());
        return new ModelAndView("edit", params);
    }
}
