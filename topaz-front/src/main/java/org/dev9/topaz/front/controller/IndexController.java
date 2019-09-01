package org.dev9.topaz.front.controller;

import org.dev9.topaz.common.entity.Topic;
import org.dev9.topaz.common.exception.PageNotFoundException;
import org.dev9.topaz.front.service.FrontTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Map;

@Controller
public class IndexController {
    @Resource(name = "FrontTopicService")
    private FrontTopicService frontTopicService;

    @GetMapping({"/index", "/", ""})
    public String index(@RequestParam(value = "page", defaultValue = "0") Integer page,
                        @RequestParam(value = "limit", defaultValue = "20") Integer limit,
                        Map<String, Object> map){
        Page<Topic> topics=frontTopicService.findAllTopicPages(page, limit);

        map.put("topics", topics.getContent());
        map.put("page", page);
        map.put("limit", limit);
        map.put("pageCount", topics.getTotalPages());

        return "index";
    }

    @GetMapping("/test")
    public String test() throws PageNotFoundException {
        if (true)
            throw new PageNotFoundException();

        return "index";
    }
}
