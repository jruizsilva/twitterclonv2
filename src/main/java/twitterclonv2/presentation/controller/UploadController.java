package twitterclonv2.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import twitterclonv2.business.service.UploadService;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class UploadController {
    private final UploadService uploadService;

    @PostMapping("/profileImage")
    public ResponseEntity<String> uploadProfileImage(@RequestParam("profileImage")
                                                     MultipartFile profileImage) {
        return ResponseEntity.ok(uploadService.uploadProfileImage(profileImage));
    }

    @DeleteMapping("/profileImage")
    public ResponseEntity<String> deleteProfileImage() {
        return ResponseEntity.ok(uploadService.deleteProfileImage());
    }

    @PostMapping("/backgroundImage")
    public ResponseEntity<String> uploadBackgroundImage(@RequestParam("backgroundImage")
                                                        MultipartFile backgroundImage) {
        return ResponseEntity.ok(uploadService.uploadBackgroundImage(backgroundImage));
    }
}
