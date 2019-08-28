package org.dev9.topaz.api.service;

import org.dev9.topaz.common.entity.Comment;

public interface CommentService {
    Boolean saveComment(Comment comment);
}
