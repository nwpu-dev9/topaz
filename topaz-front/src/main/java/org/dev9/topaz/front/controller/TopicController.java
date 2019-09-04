package org.dev9.topaz.front.controller;

import org.dev9.topaz.common.dao.repository.TopicRepository;
import org.dev9.topaz.common.entity.Topic;
import org.dev9.topaz.common.exception.PageNotFoundException;
import org.dev9.topaz.common.exception.UnauthorizedException;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class TopicController {
    @Resource
    private TopicRepository topicRepository;

    @GetMapping(value = "/topic/{topicId}")
    public ModelAndView getTopic(@PathVariable Integer topicId) {
        HashMap<String, Object> params = new HashMap<>();
        Optional<Topic> topic = topicRepository.findById(topicId);
        if (!topic.isPresent()) {
            throw new PageNotFoundException();
        }
        params.put("topic", topic.get());
        return new ModelAndView("topic", params);
    }

    @GetMapping(value = "/search")
    public ModelAndView searchTopic(@RequestParam String query) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("query", query);
        return new ModelAndView("search", params);
    }

    @GetMapping("/topic/new")
    public ModelAndView newPost(HttpSession session, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (session.getAttribute("userId") == null) {
            redirectAttributes.addAttribute("return", request.getRequestURI());
            return new ModelAndView("redirect:/login", new ModelMap());
        }
        Map<String, Object> params = new HashMap<>();
        params.put("topic", null);
        return new ModelAndView("edit", params);
    }

    @GetMapping("/topic/edit")
    public ModelAndView editTopic(@RequestParam Integer topicId, HttpSession session, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        Optional<Topic> topic = topicRepository.findById(topicId);
        if (!topic.isPresent()) {
            throw new PageNotFoundException();
        }
        if (!topic.get().getPoster().getUserId().equals(session.getAttribute("userId"))) {
            redirectAttributes.addAttribute("return", request.getRequestURL());
            return new ModelAndView("redirect:/login", new ModelMap());
        }
        params.put("topic", topic.get());
        return new ModelAndView("edit", params);
    }

}
