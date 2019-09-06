package org.dev9.topaz.api.controller;

import jnr.ffi.annotations.In;
import org.apache.commons.lang3.StringUtils;
import org.dev9.topaz.api.dao.repository.MessageSearchRepository;
import org.dev9.topaz.api.exception.ApiForbiddenException;
import org.dev9.topaz.api.exception.ApiNotFoundException;
import org.dev9.topaz.api.exception.ApiUnauthorizedException;
import org.dev9.topaz.api.model.RESTfulResponse;
import org.dev9.topaz.api.model.result.MessageSearchResult;
import org.dev9.topaz.common.annotation.Permission;
import org.dev9.topaz.common.dao.repository.MessageRepository;
import org.dev9.topaz.common.dao.repository.UserRepository;
import org.dev9.topaz.common.entity.Message;
import org.dev9.topaz.common.entity.User;
import org.dev9.topaz.common.enums.PermissionType;
import org.dev9.topaz.common.util.SensitiveWordUtil;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.List;

@Controller("ApiMessageController")
@RequestMapping("/api")
public class MessageController {

    @Resource
    private MessageRepository messageRepository;

    @Resource
    private MessageSearchRepository messageSearchRepository;

    @Resource
    private UserRepository userRepository;

    @GetMapping("/message/{id}")
    @ResponseBody
    @Permission(PermissionType.USER)
    public ResponseEntity<RESTfulResponse> getMessage(@PathVariable("id") Integer messageId, HttpSession session){
        Message message=messageRepository.findById(messageId).orElse(null);

        if (null == message)
            throw new ApiNotFoundException("no such message");

        Integer userId=(Integer) session.getAttribute("userId");
        Integer senderId=message.getSender().getUserId();
        Integer receiverId=message.getReceiver().getUserId();
        User user=userRepository.findById(userId).orElse(null);

        if (null == user)
            throw new ApiNotFoundException("no such user");

        if (!userId.equals(senderId) && !userId.equals(receiverId) && !user.isAdmin())
            throw new ApiUnauthorizedException("you can not get this message");


        RESTfulResponse<Message> response=RESTfulResponse.ok();
        response.setData(message);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/message/{id}")
    @ResponseBody
    @Permission(PermissionType.ADMIN)
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

        if (StringUtils.isBlank(content))
            throw new ApiNotFoundException("content can not be empty");

        if (receiverId.equals(senderId))
            throw new ApiNotFoundException("send messages to oneself is disallowed");

        User receiver=userRepository.findById(receiverId).orElse(null);
        User sender=userRepository.findById(senderId).orElse(null);

        if (null == receiver || null == sender)
            throw new ApiNotFoundException("no such receiver or sender");

        content=SensitiveWordUtil.filter(content);
        Message message=new Message(content, Instant.now(), false, sender, receiver, false);
        messageRepository.save(message);
        return ResponseEntity.ok(RESTfulResponse.ok());
    }

    @GetMapping("/user/message")
    @ResponseBody
    @Permission(PermissionType.USER)
    public ResponseEntity<RESTfulResponse> getUserMessages(Boolean receiver, HttpSession session){
        Integer userId=(Integer) session.getAttribute("userId");

        User user=userRepository.findById(userId).orElse(null);
        List<MessageSearchResult> messages=messageSearchRepository.findAllByReceiver(user, Sort.by("sentTime"));
        messages.addAll(messageSearchRepository.findAllBySender(user, Sort.by("sentTime")));

        RESTfulResponse<List<MessageSearchResult>> response=RESTfulResponse.ok();
        response.setData(messages);
        return ResponseEntity.ok(response);
    }


}
