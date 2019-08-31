package org.dev9.topaz.common.dao.repository;

import org.dev9.topaz.common.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import th.co.geniustree.springdata.jpa.repository.JpaSpecificationExecutorWithProjection;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Integer>,
        JpaSpecificationExecutorWithProjection<Topic>, JpaSpecificationExecutor<Topic> {
    List<Topic> findByTitleLike(String titleLike);
}
