package com.extension.findyourmeme.dao;

import com.extension.findyourmeme.entity.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;


@Repository
public interface ImageDao extends JpaRepository<Image,Long> {
    @Query("SELECT DISTINCT i FROM Image i JOIN i.tags t WHERE t.tagName LIKE %:tagName% AND i.isVerified = true order by i.baseAdditionalFields.createDate DESC ")
    Page<Image > findByTag(@Param("tagName") String tagName, Pageable pageable);

    @Query("SELECT COUNT(DISTINCT i) FROM Image i JOIN i.tags t WHERE t.tagName LIKE %:tagName% AND i.isVerified = true")
    Integer countImagesByTag(@Param("tagName") String tagName);

    long countByIsVerifiedFalse();

    long countByIsVerifiedTrue();

    Collection<Image> findAllByIsVerifiedFalse();
    Collection<Image> findAllByIsVerifiedTrue();
}