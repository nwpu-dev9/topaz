package org.dev9.topaz.api.dao.repository;

import org.dev9.topaz.api.model.result.MessageSearchResult;
import org.dev9.topaz.common.entity.Message;
import org.dev9.topaz.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import th.co.geniustree.springdata.jpa.repository.JpaSpecificationExecutorWithProjection;

import java.util.List;
import java.util.Optional;

public interface MessageSearchRepository extends JpaRepository<Message, Integer>,
        JpaSpecificationExecutorWithProjection<Message>, JpaSpecificationExecutor<Message> {
    List<MessageSearchResult> findAllByReceiver(User receiver);
    List<MessageSearchResult> findAllBySender(User sender);
}
