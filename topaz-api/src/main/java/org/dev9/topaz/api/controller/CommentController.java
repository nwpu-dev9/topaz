package org.dev9.topaz.api.controller;


import org.dev9.topaz.api.model.RESTfulResponse;
import org.dev9.topaz.api.service.CommentService;
import org.dev9.topaz.common.entity.Comment;
import org.dev9.topaz.common.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class CommentController {
    // @Resource("ApiCommentService")
    @Autowired
    private CommentService commentService;

    @PostMapping({"/comment/"})
    @ResponseBody
    public RESTfulResponse addComment(Comment comment) {
        RESTfulResponse response=null;

        if (null == response && null == comment.getCommentId())
            response=RESTfulResponse.fail("403");

        commentService.addComment(comment);
        return RESTfulResponse.ok();
    }
}
