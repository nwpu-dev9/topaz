package org.dev9.topaz.api.controller;

import org.apache.catalina.WebResourceRoot;
import org.dev9.topaz.api.exception.ApiNotFoundException;
import org.dev9.topaz.api.model.RESTfulResponse;
import org.dev9.topaz.common.annotation.Permission;
import org.dev9.topaz.common.dao.repository.MessageRepository;
import org.dev9.topaz.common.dao.repository.UserRepository;
import org.dev9.topaz.common.entity.Message;
import org.dev9.topaz.common.entity.User;
import org.dev9.topaz.common.enums.PermissionType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.Instant;

@Controller("ApiMessageController")
@RequestMapping("/api")
public class MessageController {

    @Resource
    private MessageRepository messageRepository;

    @Resource
    private UserRepository userRepository;

    @DeleteMapping("/message/{id}")
    @ResponseBody
    public ResponseEntity<RESTfulResponse> deleteMessage(@PathVariable(name = "id") Integer messageId) throws ApiNotFoundException {
        if (null == messageId)
            throw new ApiNotFoundException("no such message");

        messageRepository.deleteById(messageId);
        return ResponseEntity.ok(RESTfulResponse.ok());
    }

    @PostMapping("/user/{receiverId}/message")
    @ResponseBody
    @Permission(PermissionType.USER)
    public ResponseEntity<RESTfulResponse> addMessage(@PathVariable("receiverId") Integer receiverId,
                                                      @RequestParam String content,
                                                      HttpSession session) throws ApiNotFoundException {
        Integer senderId=(Integer) session.getAttribute("userId");
        User receiver=userRepository.findById(receiverId).orElse(null);
        User sender=userRepository.findById(senderId).orElse(null);

        if (null == receiver || null == sender)
            throw new ApiNotFoundException("no such receiver or sender");

        Message message=new Message(content, Instant.now(), false, sender, receiver);
        messageRepository.save(message);
        return ResponseEntity.ok(RESTfulResponse.ok());
    }
}
