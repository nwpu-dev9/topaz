package org.dev9.topaz.api.controller;

import jnr.ffi.annotations.In;
import org.dev9.topaz.api.exception.ApiNotFoundException;
import org.dev9.topaz.api.exception.ApiUnauthorizedException;
import org.dev9.topaz.api.model.RESTfulResponse;
import org.dev9.topaz.api.service.UserService;
import org.dev9.topaz.common.annotation.Permission;
import org.dev9.topaz.common.dao.repository.TopicRepository;
import org.dev9.topaz.common.dao.repository.UserRepository;
import org.dev9.topaz.common.entity.Topic;
import org.dev9.topaz.common.entity.User;
import org.dev9.topaz.common.enums.PermissionType;
import org.dev9.topaz.common.util.SensitiveWordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
                                                                 @RequestParam(required = false) String profile) throws ApiNotFoundException {
        User user = userRepository.findById(id).orElse(null);

        if (null == user)
            throw new ApiNotFoundException("no such user");

        if (null != phoneNumber)
            user.setPhoneNumber(phoneNumber);
        if (null != name)
            user.setName(name);
        if (null != profile)
            user.setProfile(SensitiveWordUtil.filter(profile));
        if (null != password)
            user.changePassword(password);
        // TODO: how to get hashed password
        logger.info(user.toString());

        userService.updateUserInformation(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(RESTfulResponse.ok());
    }

    @PostMapping(value = "/user/{id}/favorite")
    @ResponseBody
    @Permission(PermissionType.USER)
    public ResponseEntity<RESTfulResponse> addFavoriteTopic(@PathVariable("id") Integer id,
                                                            @RequestParam Integer topicId,
                                                            HttpSession session) throws ApiNotFoundException, ApiUnauthorizedException {
        User user = userRepository.findById(id).orElse(null);
        Topic topic = topicRepository.findById(topicId).orElse(null);
        Integer sessionUserId=(Integer)session.getAttribute("userId");

        if (!sessionUserId.equals(id))
            throw new ApiUnauthorizedException();

        if (user == null)
            throw new ApiNotFoundException("no such user");

        if (topic == null)
            throw new ApiNotFoundException("no such topic");

        userService.addFavoriteTopic(user, topic);
        return ResponseEntity.status(HttpStatus.CREATED).body(RESTfulResponse.ok());
    }

    @PostMapping("/user")
    @ResponseBody
    public ResponseEntity<RESTfulResponse> register(@RequestParam String name,
                                                    @RequestParam String password,
                                                    @RequestParam(defaultValue = "") String phoneNumber,
                                                    HttpSession session,
                                                    HttpServletResponse response) throws ApiNotFoundException {
        // TODO: available checking
        if (password.length()<4)
            throw new ApiNotFoundException("password is too weak");

        if (phoneNumber.length()<4)
            throw new ApiNotFoundException("phone number is not available");

        if (SensitiveWordUtil.isContainSensitiveWords(name))
            throw new ApiNotFoundException("user name not available");

        if (null != userRepository.findByName(name))
            throw new ApiNotFoundException("user name exists");

        if (null != userRepository.findByPhoneNumber(name))
            throw new ApiNotFoundException("phone number exists");

        User user=new User(name, phoneNumber, password, Instant.now(), false);

        userRepository.save(user);
        userLogin(user, session, response);
        return ResponseEntity.status(HttpStatus.CREATED).body(RESTfulResponse.ok());
    }

    @PostMapping("/user/token")
    @ResponseBody
    public ResponseEntity<RESTfulResponse> login(@RequestParam String name,
                                                 @RequestParam String password,
                                                 HttpSession session,
                                                 HttpServletResponse response) throws ApiNotFoundException {
        User user=userRepository.findByName(name);

        if (null == user)
            throw new ApiNotFoundException("no such user");

        if (!user.verifyPassword(password))
            throw new ApiNotFoundException("password incorrect");

        userLogin(user, session, response);
        return ResponseEntity.status(HttpStatus.CREATED).body(RESTfulResponse.ok());
    }

    @DeleteMapping("/user/token")
    @ResponseBody
    public ResponseEntity<RESTfulResponse> logout(HttpSession session){
        session.removeAttribute("userId");
        session.removeAttribute("userName");

        return ResponseEntity.ok(RESTfulResponse.ok());
    }

    private void userLogin(User user, HttpSession session, HttpServletResponse response){
        session.setAttribute("userId", user.getUserId());
        session.setAttribute("userName", user.getName());
        response.addCookie(new Cookie("userId", user.getUserId().toString()));
        response.addCookie(new Cookie("userName", user.getName()));
    }
}
