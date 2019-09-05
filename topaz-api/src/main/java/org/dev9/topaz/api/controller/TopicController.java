package org.dev9.topaz.api.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.Response;
import org.dev9.topaz.api.exception.ApiNotFoundException;
import org.dev9.topaz.api.model.RESTfulResponse;
import org.dev9.topaz.api.service.CommentService;
import org.dev9.topaz.api.service.TopicService;
import org.dev9.topaz.common.TopazCommonApplication;
import org.dev9.topaz.common.annotation.Permission;
import org.dev9.topaz.common.dao.repository.TopicRepository;
import org.dev9.topaz.common.dao.repository.UserRepository;
import org.dev9.topaz.common.entity.Topic;
import org.dev9.topaz.common.entity.User;
import org.dev9.topaz.common.enums.PermissionType;
import org.dev9.topaz.common.util.SensitiveWordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

        if (StringUtils.isBlank(content))
            throw new ApiNotFoundException("content can not be empty");

        if (StringUtils.isBlank(title))
            throw new ApiNotFoundException("title can not be empty");

        Topic topic = new Topic();
        topic.setTitle(SensitiveWordUtil.filter(title));
        topic.setContent(SensitiveWordUtil.filter(content));
        topic.setFavoriteCount(0);
        topic.setPostTime(Instant.now());
        topic.setVisitedCount(0);
        topic.setPoster(userRepository.findById(posterId).orElse(null));
        topic.setAudited(false);

        logger.info(topic.toString());

        if (topic.getPoster() == null)
            throw new ApiNotFoundException("no such poster");

        Topic savedTopic=topicService.saveTopic(topic) ;
        RESTfulResponse<Integer> response=RESTfulResponse.ok();

        response.setData(savedTopic.getTopicId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(path = "/topic/{id}")
    @ResponseBody
    @Permission(PermissionType.USER)
    public ResponseEntity<RESTfulResponse> updateTopic(@PathVariable("id") Integer topicId,
                                                       @RequestParam(required = false) String title,
                                                       @RequestParam(required = false) String content,
                                                       HttpSession session
    ) throws ApiNotFoundException {
        Integer posterId=(Integer) session.getAttribute("userId");
        Topic topic=topicRepository.findById(topicId).orElse(null);

        if (null == topic)
            throw new ApiNotFoundException("no such topic");

        if (null == topic.getPoster() || !posterId.equals(topic.getPoster().getUserId()))
            throw new ApiNotFoundException("no such poster or poster does not correspond");

        if (null != content) {
            if (StringUtils.isBlank(content))
                throw new ApiNotFoundException("content can not be empty");
            topic.setContent(SensitiveWordUtil.filter(content));
        }

        if (null != title) {
            if (StringUtils.isBlank(title))
                throw new ApiNotFoundException("title can not be empty");
            topic.setTitle(SensitiveWordUtil.filter(title));
        }

        topic.setAudited(false);
        Topic savedTopic=topicService.saveTopic(topic);
        RESTfulResponse<Integer> response=RESTfulResponse.ok();

        response.setData(savedTopic.getTopicId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping(path = "/admin/topic/{id}")
    @ResponseBody
    @Permission(PermissionType.ADMIN)
    public ResponseEntity <RESTfulResponse> deleteTopic(@PathVariable("id") Integer id){
        topicRepository.deleteById(id);
        return ResponseEntity.ok(RESTfulResponse.ok());
    }
}