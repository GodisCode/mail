package me.god.mail.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.net.URLEncoder;

/**
 * File操作工具类
 */
public class FileUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 转化文件名称的编码
     * @param fileName
     * @return
     */
    public static String encodeFileName(String fileName) {
        try {
            fileName = URLEncoder.encode(fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("文件名称编码转换失败！");
            }
        }
        return fileName;
    }

    /**
     * 获取文件扩展名（包含.符号）
     * @param fileName 文件名称（包含扩展名）
     * @return
     */
    public static String getFileExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        int start = fileName.lastIndexOf(".");
        if (start == -1) {
            return null;
        }
        return fileName.substring(start);
    }

    /**
     * 合并原始文件名和自定义文件名，获取新文件名（包含扩展名）
     * @param originalFileName
     * @param customFileName
     * @return
     */
    public static String getFileName(String originalFileName, String customFileName) {
        // 1、是否空值判断
        boolean hasOriginal = StringUtils.isNotBlank(originalFileName);
        boolean hasCustom = StringUtils.isNotBlank(customFileName);

        if (!hasOriginal && !hasCustom) {
            throw new IllegalArgumentException("没有指定文件名");
        }

        if (hasOriginal && !hasCustom) {
            return originalFileName;
        }

        if (!hasOriginal) {
            return customFileName;
        }

        // 2、若两个文件名都有值，则进行是否有扩展名判断
        String originalFileExt = getFileExtension(originalFileName);
        String customFileExt = getFileExtension(customFileName);
        // originalFileName有扩展名，但customFileName没有扩展名，合并两者
        if (StringUtils.isNotBlank(originalFileExt) && StringUtils.isBlank(customFileExt)) {
            return customFileName + originalFileExt;
        }

        return customFileName;
    }
}
