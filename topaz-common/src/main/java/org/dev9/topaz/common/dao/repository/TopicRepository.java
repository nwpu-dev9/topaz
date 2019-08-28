package org.dev9.topaz.common.dao.repository;

import org.dev9.topaz.common.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Integer>, JpaSpecificationExecutor<Topic> {
    List<Topic> findByTitleLike(String titleLike);
}
