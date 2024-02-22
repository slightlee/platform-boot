package com.demain.framework.core.enums;

/**
 * 文件后缀枚举
 *
 * @author demain_lee
 * @since 2024/02/22
 */
public enum FileExtensionEnum {

    TXT("txt"),
    PDF("pdf"),
    DOCX("docx"),
    DOC("doc"),
    XLSX("xlsx"),
    XLS("xls"),
    CSV("csv"),
    pptx("pptx"),
    ppt("ppt"),
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    GIF("gif"),
    BMP("bmp"),
    MP3("mp3"),
    WAV("wav"),
    MP4("mp4"),
    AVI("avi"),
    MOV("mov"),
    FLV("flv"),
    RMVB("rmvb"),
    WMV("wmv"),
    MKV("mkv"),
    APK("apk"),
    ZIP("zip"),
    RAR("rar");

    private final String extension;

    FileExtensionEnum(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
}
