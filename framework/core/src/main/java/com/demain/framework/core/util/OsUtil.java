package com.demain.framework.core.util;

/**
 * 操作系统工具类
 *
 * @author demain_lee
 * @since 2024/3/4
 */
@SuppressWarnings("unused")
public class OsUtil {
    
    private static final String OS = System.getProperty("os.name").toLowerCase();
    
    public static boolean isLinux() {
        return OS.contains("linux");
    }
    
    @SuppressWarnings("all")
    public static boolean isMacOS() {
        return OS.contains("mac") && OS.contains("os") && OS.contains("x");
    }
    
    @SuppressWarnings("all")
    public static boolean isMacOSX() {
        return OS.contains("mac") && OS.contains("os") && OS.contains("x");
    }
    
    @SuppressWarnings("all")
    public static boolean isWindows() {
        return OS.contains("windows");
    }
    
}