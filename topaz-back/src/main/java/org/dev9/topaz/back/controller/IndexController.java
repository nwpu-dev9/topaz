package org.dev9.topaz.back.controller;

import org.dev9.topaz.common.dao.repository.CommentRepository;
import org.dev9.topaz.common.dao.repository.MessageRepository;
import org.dev9.topaz.common.dao.repository.TopicRepository;
import org.dev9.topaz.common.dao.repository.UserRepository;
import org.dev9.topaz.common.entity.Comment;
import org.dev9.topaz.common.entity.Message;
import org.dev9.topaz.common.entity.Topic;
import org.dev9.topaz.common.entity.User;
import org.dev9.topaz.common.exception.PageNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller("BackIndexController")
@RequestMapping("/admin")
public class IndexController
{
    @Resource
    private UserRepository userRepository;

    @Resource
    private TopicRepository topicRepository;

    @Resource
    private CommentRepository commentRepository;

    @Resource
    private MessageRepository messageRepository;

    @RequestMapping({"", "/"})
    public String login(Map<String, Object> map)
    {
        return "back_login";
    }

    @RequestMapping("/manage")
    public String mainIndex(HttpSession session, Map<String, Object> map){
        if (null == session.getAttribute("userId"))
            return  "back_login";

        Integer userId=(Integer)session.getAttribute("userId");
        User user = userRepository.findById(userId).orElse(null);
        List<Topic> topics = topicRepository.findAll();
        List<Comment> comments = commentRepository.findAll();
        List<Message> messages = messageRepository.findAll();

        map.put("user", user);
        map.put("topicNumber", topics.size());
        map.put("commentNumber", comments.size());
        map.put("messageNumber", messages.size());
        return "back_index";
    }
}
