package com.demain.framework.core.util;

/**
 * 字符串工具类
 *
 * @author demain_lee
 * @since 2023/12/25
 */
public final class StrUtil {

    /**
     * 获取后缀
     *
     * @param fileName 123456.jpg
     * @return jpg
     */
    public static String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 获取前缀
     *
     * @param fileName 123456.jpg
     * @return 123456
     */
    public static String getPrefix(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

}
