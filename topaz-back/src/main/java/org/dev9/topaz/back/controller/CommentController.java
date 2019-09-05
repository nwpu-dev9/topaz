package org.dev9.topaz.back.controller;


import org.dev9.topaz.common.dao.query.CommentQuery;
import org.dev9.topaz.common.dao.repository.CommentRepository;
import org.dev9.topaz.common.dao.repository.MessageRepository;
import org.dev9.topaz.common.dao.repository.TopicRepository;
import org.dev9.topaz.common.entity.Comment;
import org.dev9.topaz.common.entity.Message;
import org.dev9.topaz.common.entity.Topic;
import org.dev9.topaz.common.exception.PageNotFoundException;
import org.jruby.RubyProcess;
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
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller("BackCommentController")
@RequestMapping("/admin/comment")
public class CommentController {

    @Resource
    private CommentRepository commentRepository;

    @Resource
    private MessageRepository messageRepository;

    @GetMapping({"", "/"})
    public String commentIndex(@RequestParam(defaultValue = "0") Integer page,
                             @RequestParam(defaultValue = "20") Integer limit,
                             @RequestParam(required = false) String query,
                             HttpSession session,
                             Map<String, Object> map){
        if (null == session.getAttribute("userId"))
            return  "back_login";
        if (null != query){
                map.put("query", query);
            return "back_comment_index_query";
        } else {
            CommentQuery commentQuery=new CommentQuery(){{
                setAuditedEqual(true);
            }};
            Page<Comment> comments=commentRepository
                    .findAll(commentQuery.toSpec(), PageRequest.of(page, limit, Sort.by("commentTime")));

            map.put("comments", comments.getContent());
            map.put("pageCount", comments.getTotalPages());
            map.put("page", page);
            map.put("limit", limit);

            return "back_comment_index";
        }
    }

    @GetMapping(path = "/audit")
    public String commentAudit(@RequestParam(defaultValue = "0") Integer page,
                               @RequestParam(defaultValue = "20") Integer limit,
                               @RequestParam(required = false) String query,
                               HttpSession session,
                               Map<String, Object> map){
        if (null == session.getAttribute("userId"))
            return  "back_login";

            Page<Comment> commentPage = commentRepository.findAll(PageRequest.of(page, limit, Sort.by("commentTime")));
            map.put("comments", commentPage.getContent());
            map.put("pageCount", commentPage.getTotalPages());
            map.put("page", page);
            map.put("limit", limit);

            return "back_comment_index_audit";
    }
}

