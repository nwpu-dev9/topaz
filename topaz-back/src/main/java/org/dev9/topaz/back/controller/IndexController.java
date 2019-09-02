package org.dev9.topaz.back.controller;

import org.dev9.topaz.common.dao.repository.UserRepository;
import org.dev9.topaz.common.entity.User;
import org.dev9.topaz.common.exception.PageNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;

@Controller("BackIndexController")
@RequestMapping("/admin")
public class IndexController
{
    @Resource
    private UserRepository userRepository;

    @GetMapping({"", "/"})
    public String mainIndex(Map<String, Object> map){

        return "back_index";
    }

    @GetMapping({"/left", "/left/"})
    public String leftIndex(Map<String, Object> map){

        return "back_left";
    }

    @GetMapping({"/right", "/right/"})
    public String rightIndex(Map<String, Object> map){

        Optional<User> user = userRepository.findById(1);
        if (!user.isPresent()) {
            throw new PageNotFoundException();
        }
        map.put("user", user.get());

        return "back_right";
    }

    @GetMapping({"/top", "/top/"})
    public String topIndex(Map<String, Object> map){

        Optional<User> user = userRepository.findById(1);
        if (!user.isPresent()) {
            throw new PageNotFoundException();
        }
        map.put("user", user.get());
        return "back_top";
    }

}
