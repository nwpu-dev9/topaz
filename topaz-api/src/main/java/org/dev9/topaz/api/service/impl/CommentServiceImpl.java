package org.dev9.topaz.api.service.impl;

import org.dev9.topaz.api.service.CommentService;
import org.dev9.topaz.common.dao.repository.CommentRepository;
import org.dev9.topaz.common.dao.repository.TopicRepository;
import org.dev9.topaz.common.dao.repository.UserRepository;
import org.dev9.topaz.common.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service("ApiCommentService")
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentRepository commentRepository;

    @Resource
    private TopicRepository topicRepository;

    @Resource
    private UserRepository userRepository;

    @Override
    public Boolean saveComment(Comment comment) {

        if (null == userRepository.findById(comment.getCommentUserId()).orElse(null))
            return false;

        if (null == topicRepository.findById(comment.getTopicId()).orElse(null))
            return false;

        if (null != commentRepository.findById(comment.getCommentId()).orElse(null))
            return false;

        // comment.setCommentId(null); // serial?
        comment.setCommentTime(new Date());

        commentRepository.save(comment);
        return true;
    }
}
