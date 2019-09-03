package org.dev9.topaz.api.controller;

import org.dev9.topaz.api.exception.ApiNotFoundException;
import org.dev9.topaz.api.model.RESTfulResponse;
import org.dev9.topaz.api.service.ImageService;
import org.dev9.topaz.common.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.StringTokenizer;

@Controller
@RequestMapping("/api")
public class ImageController {

    @Resource
    private ImageService imageService;

    @PostMapping("/image")
    @ResponseBody
    public ResponseEntity<RESTfulResponse> imageUpload(@RequestParam MultipartFile image) throws ApiNotFoundException {
        String filename=imageService.saveImage(image);

        RESTfulResponse<String> response=RESTfulResponse.ok();
        response.setData(filename);
        return ResponseEntity.ok(response);
    }
}
