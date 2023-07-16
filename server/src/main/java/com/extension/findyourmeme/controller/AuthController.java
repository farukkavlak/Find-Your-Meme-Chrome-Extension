package com.extension.findyourmeme.controller;

import com.extension.findyourmeme.dto.AuthRequestDto;
import com.extension.findyourmeme.generic.response.RestResponse;
import com.extension.findyourmeme.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity save(@RequestBody AuthRequestDto authRequestDto) {
        return ResponseEntity.ok(RestResponse.of(authService.register(authRequestDto)));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthRequestDto authRequestDto) {
        return ResponseEntity.ok(RestResponse.of(authService.login(authRequestDto)));
    }

    @PostMapping("/logout")
    public ResponseEntity logout() {
        authService.logout();
        return ResponseEntity.ok(RestResponse.of("Logout successfully"));
    }
}
