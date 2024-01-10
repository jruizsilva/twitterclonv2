package twitterclonv2.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/upload")
public class UploadController {
    @PostMapping("/profileImage")
    public ResponseEntity<String> uploadProfileImage(@RequestParam("profileImage")
                                                     MultipartFile profileImage) {
        if (profileImage.isEmpty()) {
            throw new RuntimeException("image is empty");
        }
        Path pathProfileImages = Paths.get("src/main/resources/static/profileImages");
        String absolutePathProfileImages = pathProfileImages.toFile()
                                                            .getAbsolutePath();
        try {
            byte[] bytesImg = profileImage.getBytes();
            Path fullPath = Paths.get(absolutePathProfileImages + "/" + profileImage.getOriginalFilename());
            Files.write(fullPath,
                        bytesImg);

            return ResponseEntity.ok(profileImage.getOriginalFilename());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
