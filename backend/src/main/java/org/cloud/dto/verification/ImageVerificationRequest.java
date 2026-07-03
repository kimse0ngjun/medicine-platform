package org.cloud.dto.verification;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ImageVerificationRequest {
    private MultipartFile image;
}
