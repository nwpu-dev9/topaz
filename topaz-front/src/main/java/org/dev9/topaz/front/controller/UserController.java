package org.dev9.topaz.front.controller;

import org.dev9.topaz.common.dao.repository.UserRepository;
import org.dev9.topaz.common.entity.User;
import org.dev9.topaz.common.exception.PageNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserController {
    @Resource
    UserRepository userRepository;

    @GetMapping(value = "/user/{userId}")
    public ModelAndView get(@PathVariable Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new PageNotFoundException();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("user", user.get());
        return new ModelAndView("user", params);
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
