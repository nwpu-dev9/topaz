package org.dev9.topaz.api.service.impl;

import org.dev9.topaz.api.service.CommentService;
import org.dev9.topaz.common.dao.repository.CommentRepository;
import org.dev9.topaz.common.dao.repository.TopicRepository;
import org.dev9.topaz.common.dao.repository.UserRepository;
import org.dev9.topaz.common.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.Date;

@Service("ApiCommentService")
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentRepository commentRepository;

    @Override
    public Boolean saveComment(Comment comment) {
        comment.setCommentId(null);
        comment.setCommentTime(Instant.now());

        commentRepository.save(comment);
        return true;
    }
}
