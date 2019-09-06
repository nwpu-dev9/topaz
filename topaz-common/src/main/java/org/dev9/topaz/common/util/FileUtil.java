package org.dev9.topaz.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.dev9.topaz.common.configuration.WebConfig;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

public class FileUtil {
    private static final String PATH = WebConfig.ENV_RESOURCE_PATH + "/image";

    public static String saveMultipartFile(MultipartFile file) throws Exception {
        checkPathExists();
        String filename = DigestUtils.md5Hex(file.getOriginalFilename()) + "." + getExtName(file);

        file.transferTo(new File(PATH, filename));
        return filename;
    }

    public static String getExtName(MultipartFile file) throws Exception {
        String fileOriginalName = file.getOriginalFilename();

        if (null == fileOriginalName || StringUtils.isBlank(fileOriginalName))
            throw new Exception("file extension name can not be found");

        String[] nameSplited = fileOriginalName.split("\\.");
        return nameSplited[nameSplited.length - 1];
    }

    public static Boolean isImage(String ext) {
        for (String extension : WebConfig.IMAGE_EXT_LIST) {
            if (ext.equals(extension)) return true;
        }
        return false;
    }

    private static void checkPathExists() throws Exception {
        File path = new File(PATH);
        if (!path.exists()) {
            boolean directoryCreated = path.mkdir();

            if (!directoryCreated)
                throw new Exception("server error");
        }
    }
}
