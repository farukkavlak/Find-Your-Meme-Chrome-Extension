package com.extension.findyourmeme.converter;

import com.extension.findyourmeme.dto.*;
import com.extension.findyourmeme.entity.Image;
import com.extension.findyourmeme.entity.Tag;
import com.extension.findyourmeme.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageConverter {
    private final TagService tagService;

    //Image postdto -> Image
    public Image convertToImage(ImagePostRequestDto postDto){
        Image image = Image.builder().tags(new ArrayList<>()).build();
       for(String tagName : postDto.getTags()){
            Tag tag = this.tagService.findByTagName(tagName);
            if(tag == null){
                tag = Tag.builder().tagName(tagName).images(new ArrayList<>()).build();
            }
            tag.getImages().add(image);
            image.getTags().add(tag);
        }
        image.setImagePath(postDto.getImagePath());
        return image;
    }

    //Image -> ImagesResponseDto
    public ImagesResponseDto convertToImagesDtos(Page<Image> images, Integer pageNumber){
        ImagesResponseDto imagesResponseDto = ImagesResponseDto.builder().pageNumber(pageNumber).images(new ArrayList<>()).build();
        for(Image image : images){
            List<String> tags = new ArrayList<>();
            for(Tag tag : image.getTags()){
                tags.add(tag.getTagName());
            }
            imagesResponseDto.getImages().add(ImageResponseDto.builder().imagePath(image.getImagePath()).tags(tags).build());
        }
        return imagesResponseDto;
    }
    public Collection<AdminImageResponseDto> convertToAdminImageResponseDtos(Collection<Image> images){
        Collection<AdminImageResponseDto> adminImageResponseDtos = new ArrayList<>();
        for(Image image : images){
            Collection<String> tags = new ArrayList<>();
            for(Tag tag : image.getTags()){
                tags.add(tag.getTagName());
            }
            adminImageResponseDtos.add(AdminImageResponseDto.builder().id(image.getId()).imagePath(image.getImagePath()).isVerified(image.isVerified()).tags(tags).build());
        }
        return adminImageResponseDtos;

    }

    public Collection<TagDto> convertToTagDtos(List<Tag> all) {
        Collection<TagDto> tagDtos = new ArrayList<>();
        for(Tag tag : all){
            tagDtos.add(TagDto.builder().name(tag.getTagName()).build());
        }
        return tagDtos;
    }
}
