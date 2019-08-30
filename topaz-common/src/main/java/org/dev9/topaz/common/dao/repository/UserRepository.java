package org.dev9.topaz.common.dao.repository;

import org.dev9.topaz.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import th.co.geniustree.springdata.jpa.repository.JpaSpecificationExecutorWithProjection;

public interface UserRepository extends JpaRepository<User, Integer>,
        JpaSpecificationExecutorWithProjection<User>, JpaSpecificationExecutor<User> {

    User findByName(String name);
}
