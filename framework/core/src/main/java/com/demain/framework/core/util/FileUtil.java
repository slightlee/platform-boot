package com.demain.framework.core.util;

import com.demain.framework.core.enums.FileExtensionEnum;

/**
 * 文件工具类
 *
 * @author demain_lee
 * @since 2024/02/22
 */
public class FileUtil {

    /**
     * 获取文件的后缀
     *
     * @param fileName 文件名
     * @return 文件后缀
     */
    public static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    /**
     * 检查文件后缀是否在枚举中
     *
     * @param fileName 文件名
     * @return 是否有效
     */
    public static boolean isValidFileExtension(String fileName) {
        String extension = getFileExtension(fileName);
        for (FileExtensionEnum fileExt : FileExtensionEnum.values()) {
            if (fileExt.getExtension().equals(extension)) {
                return true;
            }
        }
        return false;
    }

}