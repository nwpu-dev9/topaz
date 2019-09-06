package org.dev9.topaz.back.controller;

import org.dev9.topaz.common.dao.repository.MessageRepository;
import org.dev9.topaz.common.dao.repository.UserRepository;
import org.dev9.topaz.common.entity.Message;
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
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller("BackMessageController")
@RequestMapping("/admin/message")
public class MessageController {

    @Resource
    private UserRepository userRepository;

    @Resource
    private MessageRepository messageRepository;

    @GetMapping({"", "/"})
    public String messageIndex(@RequestParam(defaultValue = "0") Integer page,
                               @RequestParam(defaultValue = "20") Integer limit,
                               HttpSession session,
                             Map<String, Object> map){
        if (null == session.getAttribute("userId"))
            return  "back_login";

        if (!userRepository.findById((Integer) session.getAttribute("userId")).orElse(null).isAdmin())
            return  "back_login";


            Page<Message> messagePage = messageRepository.findAll(PageRequest.of(page, limit, Sort.by("sentTime")));
            map.put("messages", messagePage.getContent());
            map.put("pageCount", messagePage.getTotalPages());
            map.put("page", page);
            map.put("limit", limit);

            return "back_message_index";

    }

}