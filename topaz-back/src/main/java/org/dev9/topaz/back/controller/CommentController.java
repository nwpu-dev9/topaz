package org.dev9.topaz.back.controller;


import org.dev9.topaz.common.dao.repository.CommentRepository;
import org.dev9.topaz.common.dao.repository.TopicRepository;
import org.dev9.topaz.common.entity.Comment;
import org.dev9.topaz.common.entity.Topic;
import org.dev9.topaz.common.exception.PageNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller("BackCommentController")
@RequestMapping("/admin/comment")
public class CommentController {

    @Resource
    private CommentRepository commentRepository;

    @GetMapping({"", "/"})
    public String topicIndex(@RequestParam(defaultValue = "0") Integer page,
                             @RequestParam(defaultValue = "20") Integer limit,
                             Map<String, Object> map){
        Page<Comment> commentPage=commentRepository.findAll(PageRequest.of(page, limit, Sort.by("commentTime")));

        map.put("comments",commentPage.getContent());
        map.put("page", page);
        map.put("limit", limit);
        map.put("pageCount", commentPage.getTotalPages());

        return "back_comment_index";
    }

    @GetMapping(value = "{commentId}")
    public ModelAndView getTopic(@PathVariable Integer commentId) {
        HashMap<String, Object> params = new HashMap<>();
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (!comment.isPresent()) {
            throw new PageNotFoundException();
        }
        params.put("comment", comment.get());
        return new ModelAndView("back_comment", params);
    }
}

