package org.dev9.topaz.api.service;

import org.dev9.topaz.api.exception.ApiNotFoundException;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String saveImage(MultipartFile image) throws ApiNotFoundException;
}
