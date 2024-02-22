package com.demain.framework.core.util;

import com.demain.framework.core.enums.FileExtensionEnum;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

/**
 * 图片处理工具类
 *
 * @author demain_lee
 * @since 2024/02/21
 */
public class ImageUtil {


    /**
     * 将base64编码的图片转为BufferedImage
     *
     * @param base64Image base64 地址
     * @return image 图片
     * @throws IOException 异常
     */
    public static BufferedImage convertBase64ToImage(String base64Image) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Image);
        ByteArrayInputStream bis = new ByteArrayInputStream(decodedBytes);
        return ImageIO.read(bis);
    }

    /**
     * 保存BufferedImage图片
     *
     * @param image      图片
     * @param outputPath 保存路径
     * @throws IOException 异常
     */
    public static void saveImage(BufferedImage image, String outputPath) throws IOException {
        File outputFile = new File(outputPath);
        ImageIO.write(image, FileExtensionEnum.PNG.getExtension(), outputFile);
    }

    /**
     * 保存http图片
     *
     * @param imageUrl        图片地址(eg: http://xxx.com/xxx.png)
     * @param destinationPath 保存路径
     * @throws IOException 异常
     */
    public static void downloadImage(String imageUrl, String destinationPath) throws IOException {
        URL url = new URL(imageUrl);
        try (InputStream in = url.openStream()) {
            Path destination = Path.of(destinationPath);
            Files.copy(in, destination, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
