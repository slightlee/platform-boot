package com.demain.framework.core.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 图像处理工具类
 *
 * @author demain_lee
 * @since 2024/2/2
 */
@SuppressWarnings("unused")
public class ThumbnailUtil {
    
    /**
     * 压缩图像
     *
     * @param input 输入流
     * @param width 目标宽度
     * @param height 目标高度
     * @param format 图片格式 e.g, "jpg", "png"
     * @param quality 图片质量，0~1之间的float类型数值
     * @return 压缩后的图片输入流
     * @throws IOException IO异常
     */
    @SuppressWarnings("all")
    public static InputStream compressImage(InputStream input, int width, int height, String format,
            float quality) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(input)
                .size(width, height)
                .outputQuality(quality)
                .outputFormat(format)
                .toOutputStream(outputStream);
        byte[] bytes = outputStream.toByteArray();
        InputStream output = new ByteArrayInputStream(bytes);
        input.close();
        return output;
    }
    
    /**
     * 压缩图像
     *
     * @param originalPath 原始图片路径
     * @param compressPath 压缩后图片路径
     * @param width 目标宽度
     * @param height 目标高度
     * @param quality 图片质量，0~1之间的float类型数值
     * @throws IOException IO异常
     */
    @SuppressWarnings("unused")
    public static void toLocalFile(String originalPath, String compressPath, int width, int height,
            float quality) throws IOException {
        Thumbnails.of(new File(originalPath))
                .size(width, height)
                .outputQuality(quality)
                .toFile(new File(compressPath));
    }
    
    /**
     * 添加水印 <a href="https://github.com/coobird/thumbnailator/wiki/Examples">...</a>
     * <p>
     * eg: BufferedImage thumbnail = addWatermark("/Users/ming/Desktop/22.jpeg",
     * "/Users/ming/Desktop/1666632570906927105.jpg", 500, 500, Positions.BOTTOM_RIGHT, 0.5f); ImageIO.write(thumbnail,
     * "jpg", new File("/Users/ming/Desktop/221.jpg"));
     *
     * @param originalPath 原始图片路径
     * @param watermarkPath 水印图片路径
     * @param width 目标宽度
     * @param height 目标高度
     * @param positions 水印的位置
     * @param opacity 水印的不透明度
     * @return bufferedImage
     * @throws IOException IO异常
     */
    @SuppressWarnings("unused")
    public static BufferedImage addWatermark(String originalPath, String watermarkPath, int width, int height,
            Positions positions, float opacity) throws IOException {
        // 原始图片
        BufferedImage originalImage = ImageIO.read(new File(originalPath));
        // 水印的图像
        BufferedImage watermarkImage = ImageIO.read(new File(watermarkPath));
        return Thumbnails.of(originalImage)
                .size(width, height)
                .watermark(positions, watermarkImage, opacity)
                .asBufferedImage();
    }
    
    /**
     * 添加水印 <a href="https://github.com/coobird/thumbnailator/wiki/Examples">...</a>
     *
     * @param originalPath 原始图片路径
     * @param processPath 处理后图片路径
     * @param width 目标宽度
     * @param height 目标高度
     * @param watermarkPath 水印图片路径
     * @param positions 水印的位置
     * @param opacity 水印的不透明度
     * @throws IOException IO异常
     */
    @SuppressWarnings("unused")
    public static void addWatermark(String originalPath, String processPath, int width, int height,
            String watermarkPath, Positions positions, float opacity) throws IOException {
        // 原始图片
        BufferedImage originalImage = ImageIO.read(new File(originalPath));
        // 水印图像
        BufferedImage watermarkImage = ImageIO.read(new File(watermarkPath));
        Thumbnails.of(originalImage)
                .size(width, height)
                .watermark(positions, watermarkImage, opacity)
                .toFile(new File(processPath));
    }
    
}
