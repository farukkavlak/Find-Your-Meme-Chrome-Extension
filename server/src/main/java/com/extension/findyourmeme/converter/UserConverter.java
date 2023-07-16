package com.extension.findyourmeme.converter;

import com.extension.findyourmeme.dto.AuthRequestDto;
import com.extension.findyourmeme.dto.UserDto;
import com.extension.findyourmeme.entity._User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserConverter {
    public _User convertToUser(AuthRequestDto authRequestDto) {
        return _User.builder()
                .username(authRequestDto.getUsername())
                .password(authRequestDto.getPassword())
                .role("USER")
                .build();
    }

    public Collection<UserDto> convertToUserDtos(List<_User> all) {
        Collection<UserDto> userDtos = new ArrayList<>();
        for (_User user : all) {
            userDtos.add(UserDto.builder().id(user.getId()).username(user.getUsername()).role(user.getRole()).build());
        }
        return userDtos;
    }
}
