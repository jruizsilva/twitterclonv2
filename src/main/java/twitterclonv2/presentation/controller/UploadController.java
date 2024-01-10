package twitterclonv2.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
        /*if (profileImage.isEmpty()) {
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
        }*/
    }
}
