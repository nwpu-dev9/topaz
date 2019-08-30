package org.dev9.topaz.api.controller;

import org.dev9.topaz.api.model.RESTfulResponse;
import org.dev9.topaz.api.service.UserService;
import org.dev9.topaz.common.dao.repository.TopicRepository;
import org.dev9.topaz.common.dao.repository.UserRepository;
import org.dev9.topaz.common.entity.Topic;
import org.dev9.topaz.common.entity.User;
import org.dev9.topaz.common.util.HashingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.Instant;

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
            if (null != password)
                user.changePassword(password);
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

    @PostMapping("/user")
    @ResponseBody
    public ResponseEntity<RESTfulResponse> register(@RequestParam String name,
                                                    @RequestParam String password,
                                                    @RequestParam(defaultValue = "") String phoneNumber,
                                                    HttpSession session){
        RESTfulResponse response=null;

        // TODO: available checking
        if (password.length()<4)
            response=RESTfulResponse.fail("this password is too weak");

        if (phoneNumber.length()<4)
            response=RESTfulResponse.fail("phone number is not available");

        if (null == response && null != userRepository.findByName(name))
            response=RESTfulResponse.fail("this user name exists");

        if (null == response && null != userRepository.findByPhoneNumber(name))
            response=RESTfulResponse.fail("this phone number exists");


        if (null != response)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        User user=new User(name, phoneNumber, password, Instant.now());

        userRepository.save(user);
        session.setAttribute("userId", user.getUserId());
        session.setAttribute("userName", user.getName());
        return ResponseEntity.ok(RESTfulResponse.ok());
    }

    @GetMapping("/user/token")
    @ResponseBody
    public ResponseEntity<RESTfulResponse> login(@RequestParam String name,
                                                 @RequestParam String password,
                                                 HttpSession session){
        RESTfulResponse response=null;
        User user=userRepository.findByName(name);

        if (null == user)
            response=RESTfulResponse.fail("no such user");

        if (null == response && !user.verifyPassword(password))
            response = RESTfulResponse.fail("password incorrect");

        if (null != response)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        session.setAttribute("userId", user.getUserId());
        session.setAttribute("userName", user.getName());
        return ResponseEntity.ok(RESTfulResponse.ok());
    }

    @DeleteMapping("/user/token")
    @ResponseBody
    public ResponseEntity<RESTfulResponse> logout(HttpSession session){
        session.removeAttribute("userId");
        session.removeAttribute("userName");
        return ResponseEntity.ok(RESTfulResponse.ok());
    }
}
