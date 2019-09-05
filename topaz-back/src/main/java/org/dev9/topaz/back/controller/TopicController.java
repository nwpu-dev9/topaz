package org.dev9.topaz.back.controller;

import org.dev9.topaz.common.dao.repository.MessageRepository;
import org.dev9.topaz.common.dao.repository.TopicRepository;
import org.dev9.topaz.common.dao.repository.UserRepository;
import org.dev9.topaz.common.entity.Message;
import org.dev9.topaz.common.entity.Topic;
import org.dev9.topaz.common.entity.User;
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
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller("BackTopicController")
@RequestMapping("/admin/topic")
public class TopicController {

    @Resource
    private UserRepository userRepository;

    @Resource
    private TopicRepository topicRepository;

    @Resource
    private MessageRepository messageRepository;

    @GetMapping({"", "/"})
    public String topicIndex(@RequestParam(defaultValue = "0") Integer page,
                             @RequestParam(defaultValue = "20") Integer limit,
                             @RequestParam(required = false) String query,
                             HttpSession session,
                             Map<String, Object> map){

        if (null == session.getAttribute("userId"))
            return  "back_login";

        if (null != query){
            map.put("query", query);
            return "back_topic_index_query";
        } else {
            Page<Topic> topicPage = topicRepository.findAll(PageRequest.of(page, limit, Sort.by("postTime")));
            map.put("topics", topicPage.getContent());
            map.put("pageCount", topicPage.getTotalPages());
            map.put("page", page);
            map.put("limit", limit);

            return "back_topic_index";
        }
    }
}
