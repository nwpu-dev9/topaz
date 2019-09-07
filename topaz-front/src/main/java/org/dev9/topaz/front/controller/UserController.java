package org.dev9.topaz.front.controller;

import org.dev9.topaz.common.dao.repository.MessageRepository;
import org.dev9.topaz.common.dao.repository.UserRepository;
import org.dev9.topaz.common.entity.Message;
import org.dev9.topaz.common.entity.User;
import org.dev9.topaz.common.exception.PageNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class UserController {
    @Resource
    UserRepository userRepository;
    @Resource
    MessageRepository messageRepository;

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

    @GetMapping(value = "/messages")
    public ModelAndView getMessages(HttpSession session, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (session.getAttribute("userId") == null) {
            redirectAttributes.addAttribute("return", request.getRequestURL());
            return new ModelAndView("redirect:/login", new ModelMap());
        }
        List<Message> messages = messageRepository.findAllByReceiver(userRepository.findById((Integer) session.getAttribute("userId")).orElse(null)).orElse(null);
        Map<String, Object> params = new HashMap<>();
        if (messages == null) {
            messages = Collections.emptyList();
        }
        params.put("messages", messages);
        return new ModelAndView("message", params);
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "return", defaultValue = "/") String returnURL, ModelMap params) {
        params.put("return", returnURL);
        return "login";
    }
}
