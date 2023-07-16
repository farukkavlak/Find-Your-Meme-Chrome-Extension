package com.extension.findyourmeme.services;

import com.extension.findyourmeme.converter.ImageConverter;
import com.extension.findyourmeme.dao.ImageDao;
import com.extension.findyourmeme.dto.ImagePostRequestDto;
import com.extension.findyourmeme.entity.Image;
import com.extension.findyourmeme.entity.Tag;
import com.extension.findyourmeme.generic.enums.ErrorMessage;
import com.extension.findyourmeme.generic.exceptions.BusinessException;
import com.extension.findyourmeme.generic.service.BaseEntityService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ImageService extends BaseEntityService<Image,ImageDao>{
    public ImageService(ImageDao dao){
        super(dao);
    }
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private TagService tagService;
    @Autowired
    private ImageConverter imageConverter;
    public void save(ImagePostRequestDto postDto){
        Image image = imageConverter.convertToImage(postDto);
        this.save(image);
        for(Tag tag: image.getTags()){
            this.tagService.save(tag);
        }
    }

    public Page<Image> getImagesByTag(String tagName, Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        PageRequest pageRequest = getPageRequest(pageOptional, sizeOptional);
        Page<Image> images = imageDao.findByTag(tagName,pageRequest);
        if(images.isEmpty()){
            throw new BusinessException(ErrorMessage.IMAGE_NOT_FOUND_BY_TAG);
        }
        return images;
    }


    public int getPageNumbers(String tag, Optional<Integer> size) {
        int imageCount = imageDao.countImagesByTag(tag);
        int sizeInt = size.orElse(8);
        int pageNumbers = imageCount / sizeInt;
        if(imageCount % sizeInt != 0){
            pageNumbers++;
        }
        return pageNumbers;
    }


    public Boolean deleteImage(Long imageIdLong) {
        if(!imageDao.existsById(imageIdLong)){
            throw new BusinessException(ErrorMessage.IMAGE_NOT_FOUND);
        }else {
            Image image = imageDao.findById(imageIdLong).get();
            for(Tag tag: image.getTags()){
                tag.getImages().remove(image);
                if(tag.getImages().isEmpty()){
                    tagService.getDao().delete(tag);
                }
                tagService.save(tag);
            }
            imageDao.deleteById(imageIdLong);
        }
        return true;
    }
}
