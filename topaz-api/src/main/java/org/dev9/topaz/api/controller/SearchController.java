package org.dev9.topaz.api.controller;

import org.dev9.topaz.api.model.RESTfulResponse;
import org.dev9.topaz.common.dao.AbstractQuery;
import org.dev9.topaz.common.dao.query.CommentQuery;
import org.dev9.topaz.common.dao.query.TopicQuery;
import org.dev9.topaz.common.dao.query.UserQuery;
import org.dev9.topaz.common.dao.repository.CommentRepository;
import org.dev9.topaz.common.dao.repository.TopicRepository;
import org.dev9.topaz.common.dao.repository.UserRepository;
import org.dev9.topaz.common.entity.Comment;
import org.dev9.topaz.common.entity.Topic;
import org.dev9.topaz.common.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller("ApiSearchController")
@RequestMapping("/api")
public class SearchController {

    @Resource
    private UserRepository userRepository;

    @Resource
    private TopicRepository topicRepository;

    @Resource
    private CommentRepository commentRepository;

    @PostMapping("/user/search")
    @ResponseBody
    public ResponseEntity<RESTfulResponse<List<User>>> searchUser(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String profile,
            @RequestParam(required = false) String logicTypeString
    ){
        RESTfulResponse<List<User>> response=null;
        List<User> users;

        UserQuery userQuery=new UserQuery(){{
            setCombineLogicType(LogicType.AND);
            setUserIdEqual(userId);
            setNameLike(name);
            setPhoneNumberLike(phoneNumber);
            setProfileLike(profile);
        }};

        if ("OR".equals(logicTypeString))
            userQuery.setCombineLogicType(AbstractQuery.LogicType.OR);

        users=userRepository.findAll(userQuery.toSpec(), Sort.by("signupTime"));

        if (null == users)
            response=RESTfulResponse.fail("no such user");

        if (null != response)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);

        response=RESTfulResponse.ok();
        response.setData(users);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/topic/search")
    @ResponseBody
    public ResponseEntity<RESTfulResponse<List<Topic>>> searchTopic(
            @RequestParam(required = false) Integer topicId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String logicTypeString
    ){
        RESTfulResponse<List<Topic>> response=null;
        List<Topic> topics;

        TopicQuery topicQuery=new TopicQuery(){{
            setCombineLogicType(LogicType.AND);
            setTopicIdEqual(topicId);
            setTitleLike(title);
            setContentLike(content);
        }};

        if ("OR".equals(logicTypeString))
            topicQuery.setCombineLogicType(AbstractQuery.LogicType.OR);

        topics=topicRepository.findAll(topicQuery.toSpec(), Sort.by("postTime"));

        if (null == topics)
            response=RESTfulResponse.fail("no such topic");

        if (null != response)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(response);

        response=RESTfulResponse.ok();
        response.setData(topics);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/comment/search")
    @ResponseBody
    public ResponseEntity<RESTfulResponse<List<Comment>>> searchComment(
            @RequestParam(required = false) Integer commentId,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String logicTypeString
    ){
        RESTfulResponse<List<Comment>> response=null;
        List<Comment> comments;

        CommentQuery commentQuery=new CommentQuery(){{
            setCombineLogicType(LogicType.AND);
            setCommentIdEqual(commentId);
            setContentLike(content);
        }};

        if ("OR".equals(logicTypeString))
            commentQuery.setCombineLogicType(AbstractQuery.LogicType.OR);

        comments=commentRepository.findAll(commentQuery.toSpec(), Sort.by("commentTime"));

        if (null == comments)
            response=RESTfulResponse.fail("no such comment");

        if (null != response)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(response);

        response=RESTfulResponse.ok();
        response.setData(comments);
        return ResponseEntity.ok(response);
    }
}
