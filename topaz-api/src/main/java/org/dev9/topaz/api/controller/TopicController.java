package org.dev9.topaz.api.controller;

import org.apache.coyote.Response;
import org.dev9.topaz.api.model.RESTfulResponse;
import org.dev9.topaz.api.service.CommentService;
import org.dev9.topaz.api.service.TopicService;
import org.dev9.topaz.common.dao.repository.TopicRepository;
import org.dev9.topaz.common.dao.repository.UserRepository;
import org.dev9.topaz.common.entity.Topic;
import org.dev9.topaz.common.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


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

    @RequestMapping(path = "/post", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<RESTfulResponse> addTopic(@RequestParam String title,
                                                    @RequestParam  String content,
                                                    @RequestParam Integer posterId) {
        RESTfulResponse response = null;
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setContent(content);
        topic.setPoster(userRepository.findById(posterId).orElse(null));

        logger.info(topic.toString());

        if (topic.getPoster() == null) {
            response = RESTfulResponse.fail("no poster!");
        }
        if (topic.getTitle() == null) {
            response = RESTfulResponse.fail("no title!");
        }
        if (topic.getContent() == null) {
            response = RESTfulResponse.fail("no content!");
        }
        if (null != response )
            return ResponseEntity.status(HttpStatus.NOT_FOUND )
                       .body(response );

        topicService.saveTopic(topic) ;


        return ResponseEntity.ok(RESTfulResponse.ok() );
    }
    @RequestMapping(path = "/admin/topic/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity <RESTfulResponse> deleteTopic(@PathVariable("id") Integer id){
        RESTfulResponse response = null;
        if(id==null ) response = RESTfulResponse.fail("There is no id！");
        Topic topic =topicRepository.findById(id).orElse(null);

        if (topic == null){
            response = RESTfulResponse.fail("There is no topic！");
        }

        if(null != response )
            return ResponseEntity.status(HttpStatus.NOT_FOUND ).body(response );

        topicService.DeleteTopic(topic);
        return ResponseEntity.ok(RESTfulResponse.ok() );
    }
}