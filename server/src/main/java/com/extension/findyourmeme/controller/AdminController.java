package com.extension.findyourmeme.controller;

import com.extension.findyourmeme.dto.AdminImageResponseDto;
import com.extension.findyourmeme.dto.TagDto;
import com.extension.findyourmeme.dto.UserDto;
import com.extension.findyourmeme.generic.response.RestResponse;
import com.extension.findyourmeme.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    private final AdminService adminService;
    @GetMapping("/requestNumber")
    public ResponseEntity<RestResponse<Long>> getRequestNumber() {
        Long requestNumber = adminService.getRequestNumber();
        return ResponseEntity.ok(RestResponse.of(requestNumber));
    }
    @GetMapping("/imageNumber")
    public ResponseEntity<RestResponse<Long>> getImageNumber() {
        Long imageNumber = adminService.getImageNumber();
        return ResponseEntity.ok(RestResponse.of(imageNumber));
    }
    @GetMapping("/tagNumber")
    public ResponseEntity<RestResponse<Long>> getTagNumber() {
        Long tagNumber = adminService.getTagNumber();
        return ResponseEntity.ok(RestResponse.of(tagNumber));
    }
    @GetMapping("/getImageRequests")
    public ResponseEntity<RestResponse<Collection<AdminImageResponseDto>>> getImageRequests() {
        return ResponseEntity.ok(RestResponse.of(adminService.getImageRequests()));
    }
    @GetMapping("/getImages")
    public ResponseEntity<RestResponse<Collection<AdminImageResponseDto>>> getImages() {
        return ResponseEntity.ok(RestResponse.of(adminService.getImages()));
    }
    @GetMapping("/getTags")
    public ResponseEntity<RestResponse<Collection<TagDto>>> getTags() {
        return ResponseEntity.ok(RestResponse.of(adminService.getTags()));
    }
    @GetMapping("/getUsers")
    public ResponseEntity<RestResponse<Collection<UserDto>>> getUsers() {
        return ResponseEntity.ok(RestResponse.of(adminService.getUsers()));
    }
    @PatchMapping("/change-verification")
    public ResponseEntity<RestResponse<Boolean>> changeVerification(@RequestParam(name = "imageId") String imageId, @RequestParam(name = "status") boolean status) {
        Long imageIdLong = Long.parseLong(imageId);
        Boolean isChanged = adminService.changeVerification(imageIdLong, status);
        return ResponseEntity.ok(RestResponse.of(isChanged));
    }

    @DeleteMapping("/deleteImage")
    public ResponseEntity<RestResponse<Boolean>> deleteImage(@RequestParam(name = "imageId") String imageId) {
        Long imageIdLong = Long.parseLong(imageId);
        Boolean isDeleted = adminService.deleteImage(imageIdLong);
        return ResponseEntity.ok(RestResponse.of(isDeleted));
    }

    @PatchMapping("/changeRole")
    public ResponseEntity<RestResponse<Boolean>> changeRole(@RequestParam(name = "userId") String userId, @RequestParam(name = "role") String role) {
        Long userIdLong = Long.parseLong(userId);
        Boolean isChanged = adminService.changeRole(userIdLong, role);
        return ResponseEntity.ok(RestResponse.of(isChanged));
    }
}
