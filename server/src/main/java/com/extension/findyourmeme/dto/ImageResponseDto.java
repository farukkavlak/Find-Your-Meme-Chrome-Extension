package com.extension.findyourmeme.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ImageResponseDto {
    public String imagePath;
    public List<String> tags;
}
