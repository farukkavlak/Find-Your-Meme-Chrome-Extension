package com.extension.findyourmeme.dao;

import com.extension.findyourmeme.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagDao extends JpaRepository<Tag,Long> {
    Tag findByTagName(String tagName);
}
