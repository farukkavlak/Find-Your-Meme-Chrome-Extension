package com.extension.findyourmeme.dao;

import com.extension.findyourmeme.entity._User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<_User,Long> {
    Optional<_User> findByUsername(String username);

    boolean existsByUsername(String username);

}
