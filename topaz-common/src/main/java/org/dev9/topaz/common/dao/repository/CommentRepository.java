package org.dev9.topaz.common.dao.repository;

import org.dev9.topaz.common.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import th.co.geniustree.springdata.jpa.repository.JpaSpecificationExecutorWithProjection;

public interface CommentRepository extends JpaRepository<Comment, Integer>,
        JpaSpecificationExecutorWithProjection<Comment>, JpaSpecificationExecutor<Comment> {
}
