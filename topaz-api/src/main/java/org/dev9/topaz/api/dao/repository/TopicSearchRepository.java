package org.dev9.topaz.api.dao.repository;

import org.dev9.topaz.common.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import th.co.geniustree.springdata.jpa.repository.JpaSpecificationExecutorWithProjection;

public interface TopicSearchRepository extends JpaRepository<Topic, Integer>, JpaSpecificationExecutorWithProjection<Topic> {
}
