package com.extension.findyourmeme.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder
public class AdminImageResponseDto {
    private Long id;
    private String imagePath;
    private boolean isVerified;
    private Collection<String> tags;

}
