package org.dev9.topaz.api.service.impl;

import org.dev9.topaz.api.service.CommentService;
import org.dev9.topaz.common.dao.repository.CommentRepository;
import org.dev9.topaz.common.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service("ApiCommentService")
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentRepository commentRepository;

    @Override
    public void addComment(Comment comment) {
        if (null == comment.getCommentTime())
            comment.setCommentTime(new Date());

        commentRepository.save(comment);
    }
}
