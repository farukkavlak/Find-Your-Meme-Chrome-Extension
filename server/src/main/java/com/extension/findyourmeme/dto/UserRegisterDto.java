package com.extension.findyourmeme.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterDto {
    private String username;
    @Size(min=7)
    private String password;
}
