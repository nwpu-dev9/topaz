package org.dev9.topaz.api.controller;


import org.dev9.topaz.api.model.RESTfulResponse;
import org.dev9.topaz.api.service.CommentService;
import org.dev9.topaz.common.dao.repository.TopicRepository;
import org.dev9.topaz.common.dao.repository.UserRepository;
import org.dev9.topaz.common.entity.Comment;
import org.dev9.topaz.common.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api")
public class CommentController {
    private Logger logger= LoggerFactory.getLogger(CommentController.class);

    @Resource(name = "ApiCommentService")
    private CommentService commentService;

    @Resource
    private UserRepository userRepository;

    @Resource
    private TopicRepository topicRepository;

    @PostMapping("/comment")
    @ResponseBody
    public ResponseEntity<RESTfulResponse> addComment(@RequestParam Integer commenterId,
                                                      @RequestParam Integer topicId,
                                                      @RequestParam String content) {
        RESTfulResponse response=null;

        Comment comment=new Comment();
        comment.setCommenter(userRepository.findById(commenterId).orElse(null));
        comment.setTopic(topicRepository.findById(topicId).orElse(null));
        comment.setContent(content);

        logger.info(comment.toString());

        if (null == comment.getTopic())
            response=RESTfulResponse.fail("please enter a correct topic id");

        if (null == response && null == comment.getCommenter())
            response=RESTfulResponse.fail("please enter a correct comment user id");

        if (null != response)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);

        Boolean isSuccess=commentService.saveComment(comment);

        if (!isSuccess)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(RESTfulResponse.fail("please enter a correct comment id"));

        return ResponseEntity.ok(RESTfulResponse.ok());
    }

    @DeleteMapping("/comment")
    @ResponseBody
    public ResponseEntity<RESTfulResponse> deleteComment(@RequestParam Integer commentId){
        RESTfulResponse response=null;

        if (null == commentId)
            response=RESTfulResponse.fail();

        if (null != response)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);

        commentService.deleteComment(commentId);

        return ResponseEntity.ok(RESTfulResponse.ok());
    }
}
