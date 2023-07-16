package com.extension.findyourmeme.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder
public class ImagePostRequestDto {
    private Collection<String> tags;
    private String imagePath;
}
