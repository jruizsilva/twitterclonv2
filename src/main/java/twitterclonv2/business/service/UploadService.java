package twitterclonv2.business.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    String uploadProfileImage(MultipartFile file);
    String uploadBackgroundImage(MultipartFile file);
}
