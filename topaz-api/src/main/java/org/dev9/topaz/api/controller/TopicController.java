package org.dev9.topaz.api.controller;

import org.apache.coyote.Response;
import org.dev9.topaz.api.exception.ApiNotFoundException;
import org.dev9.topaz.api.model.RESTfulResponse;
import org.dev9.topaz.api.service.CommentService;
import org.dev9.topaz.api.service.TopicService;
import org.dev9.topaz.common.annotation.Permission;
import org.dev9.topaz.common.dao.repository.TopicRepository;
import org.dev9.topaz.common.dao.repository.UserRepository;
import org.dev9.topaz.common.entity.Topic;
import org.dev9.topaz.common.entity.User;
import org.dev9.topaz.common.enums.PermissionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;


@Controller("ApiTopicController")
@RequestMapping("/api")
public class TopicController {
    private Logger logger = LoggerFactory.getLogger(CommentController.class);
    @Resource
    TopicRepository topicRepository = null;

    @Resource(name = "ApiTopicService")
    private TopicService topicService;

    @Resource
    private UserRepository userRepository;

    @PostMapping(path = "/topic")
    @ResponseBody
    @Permission(PermissionType.USER)
    public ResponseEntity<RESTfulResponse> addTopic(@RequestParam String title,
                                                    @RequestParam String content,
                                                    HttpServletRequest request
    ) throws ApiNotFoundException {
        Integer posterId=(Integer) request.getSession().getAttribute("userId");

        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setContent(content);
        topic.setFavoriteCount(0);
        topic.setPostTime(Instant.now());
        topic.setVisitedCount(0);
        topic.setPoster(userRepository.findById(posterId).orElse(null));

        logger.info(topic.toString());

        if (topic.getPoster() == null)
            throw new ApiNotFoundException("no such poster");

        topicService.saveTopic(topic) ;
        return ResponseEntity.status(HttpStatus.CREATED).body(RESTfulResponse.ok());
    }

    @DeleteMapping(path = "/admin/topic/{id}")
    @ResponseBody
    @Permission(PermissionType.ADMIN)
    public ResponseEntity <RESTfulResponse> deleteTopic(@PathVariable("id") Integer id){
        topicRepository.deleteById(id);
        return ResponseEntity.ok(RESTfulResponse.ok());
    }
}