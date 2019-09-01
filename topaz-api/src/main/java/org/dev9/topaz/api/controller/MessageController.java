package org.dev9.topaz.api.controller;

import org.dev9.topaz.api.exception.ApiNotFoundException;
import org.dev9.topaz.api.model.RESTfulResponse;
import org.dev9.topaz.common.dao.repository.MessageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller("ApiMessageController")
@RequestMapping("/api")
public class MessageController {

    @Resource
    private MessageRepository messageRepository;

    @DeleteMapping("/message/{id}")
    @ResponseBody
    public ResponseEntity<RESTfulResponse> deleteMessage(@PathVariable(name = "id") Integer messageId) throws ApiNotFoundException {
        if (null == messageId)
            throw new ApiNotFoundException("no such message");

        messageRepository.deleteById(messageId);
        return ResponseEntity.ok(RESTfulResponse.ok());
    }
}
