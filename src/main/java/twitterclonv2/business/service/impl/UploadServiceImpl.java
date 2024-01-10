package twitterclonv2.business.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import twitterclonv2.business.service.UploadService;
import twitterclonv2.business.service.UserService;
import twitterclonv2.domain.entity.UserEntity;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {
    private final UserService userService;

    @Override
    public String uploadProfileImage(MultipartFile file) {
        UserEntity userAuthenticated = userService.findUserAuthenticated();
        try {
            String fileName = userAuthenticated.getUsername();
            byte[] bytes = file.getBytes();
            String fileOriginalName = file.getOriginalFilename();

            Long fileSize = file.getSize();
            Long maxFileSize = (long) (5 * 1024 * 1024);

            if (fileSize > maxFileSize) {
                return "File size must be less then or equal 5MB";
            }
            if (!fileOriginalName.endsWith(".jpg") && !fileOriginalName.endsWith(".jpeg") && !fileOriginalName.endsWith(".png")) {
                return "Only JPG, JPEG, PNG files are allowed!";
            }
            String fileExtension = fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
            String newFileName = fileName + fileExtension;

            File folderProfileImages = new File("uploads/profileImages");
            if (!folderProfileImages.exists()) {
                folderProfileImages.mkdirs();
            }
            Path path = Paths.get("uploads/profileImages/" + newFileName);
            Files.write(path,
                        bytes);

            String publicPath = "/profileImages/" + newFileName;
            userAuthenticated.setProfileImage(publicPath);
            userService.save(userAuthenticated);

            return publicPath;
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
