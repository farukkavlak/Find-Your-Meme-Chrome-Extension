package com.extension.findyourmeme.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ImagesResponseDto {
    private List<ImageResponseDto> images;
    private Integer pageNumber;
}
