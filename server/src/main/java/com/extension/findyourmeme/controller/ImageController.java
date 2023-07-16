package com.extension.findyourmeme.controller;

import com.extension.findyourmeme.converter.ImageConverter;
import com.extension.findyourmeme.dto.ImagePostRequestDto;
import com.extension.findyourmeme.dto.ImagesResponseDto;
import com.extension.findyourmeme.entity.Image;
import com.extension.findyourmeme.generic.enums.ErrorMessage;
import com.extension.findyourmeme.generic.exceptions.RequestValidationException;
import com.extension.findyourmeme.generic.response.RestResponse;
import com.extension.findyourmeme.services.AzureService;
import com.extension.findyourmeme.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Validated
public class ImageController {
    private final AzureService azureService;
    private final ImageService imageService;
    private final ImageConverter imageConverter;
    @PostMapping("/save")
    public ResponseEntity<RestResponse<Boolean>> saveImage(@RequestParam(value="image") MultipartFile file,@RequestParam(value="tags") Set<String> tags) throws IOException {
        if (file.isEmpty() || tags.isEmpty()) {
            throw new RequestValidationException(ErrorMessage.INVALID_REQUEST_PARAMETERS);
        }
        isFileImage(file);
        var imgPath = azureService.storeFile(file.getInputStream(),file.getSize());
        ImagePostRequestDto postDto = ImagePostRequestDto.builder().imagePath(imgPath).tags(tags).build();
        imageService.save(postDto);
        return ResponseEntity.ok(RestResponse.of(true));
    }


    @GetMapping("/by-tags")
    public ResponseEntity<RestResponse<ImagesResponseDto>> getImagesByTag(
            @RequestParam(name = "tag") String tag,
            @RequestParam(name = "page", required = false) Optional<Integer> page,
            @RequestParam(name = "size", required = false) Optional<Integer> size) {
        if (tag.isBlank()) {
            throw new RequestValidationException(ErrorMessage.INVALID_REQUEST_PARAMETERS);
        }
        tagLengthValidation(tag);
        tag = tag.toLowerCase();
        int pageNumber = imageService.getPageNumbers(tag, size);
        Page<Image> images = imageService.getImagesByTag(tag, page, size);
        ImagesResponseDto imagesResponseDto = imageConverter.convertToImagesDtos(images, pageNumber);
        return ResponseEntity.ok(RestResponse.of(imagesResponseDto));
    }

    private static void tagLengthValidation(String tag) {
        if(tag.length() < 3){
            throw new RequestValidationException(ErrorMessage.TAG_MIN_LENGTH);
        }
    }
    private static void isFileImage(MultipartFile file) {
        if(!file.getContentType().contains("image")){
            throw new RequestValidationException(ErrorMessage.FILE_IS_NOT_IMAGE);
        }
    }

}
