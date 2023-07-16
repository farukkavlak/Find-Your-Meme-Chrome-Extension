package com.extension.findyourmeme.services;

import com.extension.findyourmeme.converter.ImageConverter;
import com.extension.findyourmeme.converter.UserConverter;
import com.extension.findyourmeme.dao.ImageDao;
import com.extension.findyourmeme.dao.TagDao;
import com.extension.findyourmeme.dao.UserDao;
import com.extension.findyourmeme.dto.AdminImageResponseDto;
import com.extension.findyourmeme.dto.TagDto;
import com.extension.findyourmeme.dto.UserDto;
import com.extension.findyourmeme.entity.Image;
import com.extension.findyourmeme.entity._User;
import com.extension.findyourmeme.generic.enums.ErrorMessage;
import com.extension.findyourmeme.generic.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private TagDao tagDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ImageConverter imageConverter;
    @Autowired
    private UserConverter userConverter;

    @Autowired
    private ImageService imageService;
    public boolean changeVerification(Long imageId, boolean status) {
        Optional<Image> imageOptional = imageDao.findById(imageId);
        if(imageOptional.isPresent()){
            Image image = imageOptional.get();
            image.setVerified(status);
            imageDao.save(image);
            return true;
        }else {
            //TODO Throw error
            //throw new RuntimeException("Image not found");
            return false;
        }
    }

    public long getRequestNumber() {
        return imageDao.countByIsVerifiedFalse();
    }

    public long getImageNumber() {
        return imageDao.countByIsVerifiedTrue();
    }

    public long getTagNumber() {
        return tagDao.count();
    }

    public Collection<AdminImageResponseDto> getImageRequests() {
        return imageConverter.convertToAdminImageResponseDtos(imageDao.findAllByIsVerifiedFalse());
    }

    public Collection<AdminImageResponseDto> getImages() {
        return imageConverter.convertToAdminImageResponseDtos(imageDao.findAllByIsVerifiedTrue());
    }

    public Collection<TagDto> getTags() {
        return imageConverter.convertToTagDtos(tagDao.findAll());
    }

    public Collection<UserDto> getUsers() {
        return userConverter.convertToUserDtos(userDao.findAll());
    }

    public Boolean deleteImage(Long imageIdLong) {
        return imageService.deleteImage(imageIdLong);
    }

    public Boolean changeRole(Long userIdLong, String role) {
        //Find user by id and change role
        Optional<_User> user = userDao.findById(userIdLong);
        if(user.isPresent()){
            _User user1 = user.get();
            user1.setRole(role);
            userDao.save(user1);
            return true;
        }
        else{
            throw new BusinessException(ErrorMessage.USER_NOT_FOUND);
        }
    }
}
