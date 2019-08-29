package org.dev9.topaz.api.controller;

import org.dev9.topaz.api.model.RESTfulResponse;
import org.dev9.topaz.api.service.UserService;
import org.dev9.topaz.common.dao.repository.TopicRepository;
import org.dev9.topaz.common.dao.repository.UserRepository;
import org.dev9.topaz.common.entity.Topic;
import org.dev9.topaz.common.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller("ApiUserController")
@RequestMapping("/api")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource(name = "ApiUserService")
    private UserService userService;

    @Resource
    private UserRepository userRepository;

    @Resource
    private TopicRepository topicRepository;

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<RESTfulResponse> updateUserInformation(@PathVariable("id") Integer id,
                                                                 @RequestParam(required = false) String password,
                                                                 @RequestParam(required = false) String phoneNumber,
                                                                 @RequestParam(required = false) String name,
                                                                 @RequestParam(required = false) String profile) {
        RESTfulResponse response = null;
        User user = userRepository.findById(id).orElse(null);

        if (null == user)
            response = RESTfulResponse.fail("user not exist");

        if (null == response) {
            if (null != phoneNumber)
                user.setPhoneNumber(phoneNumber);
            if (null != name)
                user.setName(name);
            if (null != profile)
                user.setProfile(profile);
            // TODO: how to get hashed password
            logger.info(user.toString());
        }

        if (null != response)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(response);

        userService.updateUserInformation(user);
        return ResponseEntity.ok(RESTfulResponse.ok());
    }

    @RequestMapping(value = "/user/{id}/favorite", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<RESTfulResponse> addFavoriteTopic(@PathVariable("id") Integer id,
                                                            @RequestParam Integer topicId) {
        RESTfulResponse response = null;

        User user = userRepository.findById(id).orElse(null);
        Topic topic = topicRepository.findById(topicId).orElse(null);
        if (user == null)
            response = RESTfulResponse.fail("no such user");

        if (topic == null)
            response = RESTfulResponse.fail("no such topic");

        if (null != response)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(response);

        userService.addFavoriteTopic(user, topic);

        return ResponseEntity.ok(RESTfulResponse.ok());
    }
}
