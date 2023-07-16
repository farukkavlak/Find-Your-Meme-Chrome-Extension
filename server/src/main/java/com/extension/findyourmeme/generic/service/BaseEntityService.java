package com.extension.findyourmeme.generic.service;

import com.extension.findyourmeme.generic.entity.BaseAdditionalFields;
import com.extension.findyourmeme.generic.entity.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public abstract class BaseEntityService<E extends BaseEntity, D extends JpaRepository<E,Long>> {
    private static final Integer DEFAULT_PAGE = 0;
    private static final Integer DEFAULT_SIZE = 8;
    private final D dao;
    public List<E> findAll(){
        return dao.findAll();
    }
    public List<E> findAll(Optional<Integer> pageOptional, Optional<Integer> sizeOptional){

        PageRequest pageRequest = getPageRequest(pageOptional, sizeOptional);

        return findAll(pageRequest);
    }

    public List<E> findAll(Pageable pageable){

        Page<E> page = dao.findAll(pageable);

        return page.toList();
    }
    public Optional<E> findById(Long id){
        return dao.findById(id);
    }
    public E save(E entity){

        setAdditionalFields(entity);

        return dao.save(entity);
    }

    private void setAdditionalFields(E entity) {

        BaseAdditionalFields baseAdditionalFields = entity.getBaseAdditionalFields();

        if (baseAdditionalFields == null){
            baseAdditionalFields = new BaseAdditionalFields();
            entity.setBaseAdditionalFields(baseAdditionalFields);
        }

        if (entity.getBaseAdditionalFields().getCreateDate() == null){
            baseAdditionalFields.setCreateDate(new Date());
        }

        baseAdditionalFields.setUpdateDate(new Date());
    }
    public void delete(E e){
        dao.delete(e);
    }
    public D getDao() {
        return dao;
    }
    protected PageRequest getPageRequest(Optional<Integer> pageOptional, Optional<Integer> sizeOptional){

        Integer page = getPage(pageOptional);
        Integer size = getSize(pageOptional, sizeOptional);

        PageRequest pageRequest = PageRequest.of(page, size);

        return pageRequest;
    }

    protected Integer getSize(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        Integer size = DEFAULT_SIZE;
        if (pageOptional.isPresent()){
            size = sizeOptional.get();
        }
        return size;
    }

    protected Integer getPage(Optional<Integer> pageOptional) {
        Integer page = DEFAULT_PAGE;
        if (pageOptional.isPresent()){
            page = pageOptional.get();
        }
        return page;
    }
}
