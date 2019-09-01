package org.dev9.topaz.back.controller;

import org.dev9.topaz.common.dao.repository.MessageRepository;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller("BackMessageController")
@RequestMapping("/admin/message")
public class MessageController {

    @Resource
    private MessageRepository messageRepository;

    @GetMapping({"", "/"})
    public String topicIndex(@RequestParam(defaultValue = "0") Integer page,
                             @RequestParam(defaultValue = "20") Integer limit,
                             Map<String, Object> map){
        Page<Message> messagePage=messageRepository.findAll(PageRequest.of(page, limit, Sort.by("sentTime")));

        map.put("messages",messagePage.getContent());
        map.put("page", page);
        map.put("limit", limit);
        map.put("pageCount", messagePage.getTotalPages());

        return "back_message_index";
    }


    @GetMapping(value = "{messageId}")
    public ModelAndView getMessage(@PathVariable Integer messageId) {
        HashMap<String, Object> params = new HashMap<>();
        Optional<Message> message = messageRepository.findById(messageId);
        if (!message.isPresent()) {
            throw new PageNotFoundException();
        }
        params.put("message", message.get());
        return new ModelAndView("back_message", params);
    }
}