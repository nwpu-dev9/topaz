package org.dev9.topaz.front.controller;

import org.dev9.topaz.common.dao.repository.TopicRepository;
import org.dev9.topaz.common.entity.Topic;
import org.dev9.topaz.common.exception.PageNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Enumeration;
import java.util.Map;

@Controller
public class IndexController {
    @Resource
    private TopicRepository topicRepository;

    class Pager implements Enumeration<Integer> {
        int pageCount;
        int counter = 1;

        public Pager(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getPageCount() {
            return pageCount;
        }

        public int getCounter() {
            return counter;
        }

        public boolean hasPrevious() {
            return counter != 1;
        }

        public boolean hasNext() {
            return counter != pageCount;
        }

        @Override
        public boolean hasMoreElements() {
            return counter <= pageCount;
        }

        @Override
        public Integer nextElement() {
            if (counter <= pageCount) {
                int page = counter;
                counter++;
                return page;
            } else {
                return null;
            }
        }
    }

    @GetMapping(value = "/")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "20") Integer limit,
                        Map<String, Object> map) {

        Page<Topic> topics = topicRepository.findAll(PageRequest.of(page - 1, limit, Sort.by("postTime")));
        map.put("topics", topics.getContent());
        map.put("page", page);
        map.put("limit", limit);
        map.put("pager", new Pager(topics.getTotalPages()));
        return "index";
    }

    @GetMapping("/test")
    public String test() throws PageNotFoundException {
        if (true)
            throw new PageNotFoundException();

        return "index";
    }
}
