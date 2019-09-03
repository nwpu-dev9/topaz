package org.dev9.topaz.api.service.impl;

import org.dev9.topaz.api.exception.ApiNotFoundException;
import org.dev9.topaz.api.service.ImageService;
import org.dev9.topaz.common.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    @Override
    public String saveImage(MultipartFile image) throws ApiNotFoundException {
        try {
            String fileExtName=FileUtil.getExtName(image);
            String filename=FileUtil.saveMultipartFile(image);

            if (!FileUtil.isImage(fileExtName))
                throw new ApiNotFoundException("file extension is not allowed");

            FileUtil.saveMultipartFile(image);

            return filename;
        } catch (IOException e) {
            throw new ApiNotFoundException("server error");
        } catch (Exception e){
            throw new ApiNotFoundException(e.getMessage());
        }
    }
}
