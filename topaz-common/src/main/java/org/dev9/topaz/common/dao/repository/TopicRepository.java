package org.dev9.topaz.common.dao.repository;

import org.dev9.topaz.common.entity.Topic;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import th.co.geniustree.springdata.jpa.repository.JpaSpecificationExecutorWithProjection;

import java.awt.print.Pageable;
import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Integer>,
        JpaSpecificationExecutorWithProjection<Topic>, JpaSpecificationExecutor<Topic> {
    List<Topic> findByTitleLike(String titleLike);
}

