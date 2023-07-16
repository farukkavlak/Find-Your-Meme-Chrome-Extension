package com.extension.findyourmeme.services;

import com.extension.findyourmeme.dao.TagDao;
import com.extension.findyourmeme.entity.Tag;
import com.extension.findyourmeme.generic.service.BaseEntityService;
import org.springframework.stereotype.Service;

@Service
public class TagService extends BaseEntityService<Tag, TagDao> {
    public TagService(TagDao dao) {
        super(dao);
    }
    public Tag findByTagName(String tagName){
        return this.getDao().findByTagName(tagName);
    }
}
