package org.dev9.topaz.api.controller;


import org.dev9.topaz.api.exception.ApiNotFoundException;
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

@Controller("ApiCommentController")
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
                                                      @RequestParam String content) throws ApiNotFoundException {
        Comment comment=new Comment();
        comment.setCommenter(userRepository.findById(commenterId).orElse(null));
        comment.setTopic(topicRepository.findById(topicId).orElse(null));
        comment.setContent(content);

        logger.info(comment.toString());

        if (null == comment.getTopic())
            throw new ApiNotFoundException("no such topic");

        if (null == comment.getCommenter())
            throw new ApiNotFoundException("no such user");

        Boolean isSuccess=commentService.saveComment(comment);
        if (!isSuccess)
            throw new ApiNotFoundException("please enter a correct comment id");
        return ResponseEntity.status(HttpStatus.CREATED).body(RESTfulResponse.ok());
    }

    @DeleteMapping("/admin/comment/{id}")
    @ResponseBody
    public ResponseEntity<RESTfulResponse> deleteComment(@PathVariable("id") Integer commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(RESTfulResponse.ok());
    }
}
