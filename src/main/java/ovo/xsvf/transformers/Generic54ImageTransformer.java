package ovo.xsvf.transformers;

import ovo.xsvf.FileUtils;
import ovo.xsvf.api.ITransformer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

public class Generic54ImageTransformer implements ITransformer {
    @Override
    public void transform(File inDir, File outDir, HashMap<String, Boolean> processed) throws IOException {
        Path containerPath = Path.of("assets", "minecraft", "textures", "gui", "container");
        Path generic54Path = containerPath.resolve("generic_54.png");
        Path shulkerBoxPath = containerPath.resolve("shulker_box.png");

        File generic54File = inDir.toPath().resolve(generic54Path).toFile();
        if (!generic54File.exists())  {
            System.out.println("generic_54.png not found. skipping!");
            return;
        }
        BufferedImage img = ImageIO.read(generic54File);

        int width = img.getWidth();
        int height = img.getHeight();

        int scaleFactor ;
        if (width == 256 && height == 256) {
            scaleFactor = 1;
        } else if (width == 512 && height == 512) {
            scaleFactor = 2;
        } else if (width == 1024 && height == 1024) {
            scaleFactor = 4;
        } else if (width == 2048 && height == 2048) {
            scaleFactor = 8;
        } else {
            System.out.println("Unsupported image size for 'generic_54.png': " + width + "x" + height);
            return;
        }

        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = newImg.createGraphics();
        g2d.drawImage(img, 0, 0, null);

        // 修改图像：清空区域
        for (int x = 0; x < 176 * scaleFactor; x++) {
            for (int y = 71 * scaleFactor; y < 127 * scaleFactor; y++) {
                newImg.setRGB(x, y, 0); // 设置透明像素
            }
        }
        // 复制并清空其他区域
        for (int x = 0; x < 176 * scaleFactor; x++) {
            for (int y = 127 * scaleFactor; y < 222 * scaleFactor; y++) {
                newImg.setRGB(x, y - 56 * scaleFactor, img.getRGB(x, y));
                newImg.setRGB(x, y, 0); // 设置透明像素
            }
        }
        g2d.dispose();

        // 保存新的图像
        File shulkerBoxFile = outDir.toPath().resolve(shulkerBoxPath).toFile();
        if (!FileUtils.checkAndCreateParentDir(shulkerBoxFile)) {
            throw new IOException("Failed to create directory: " + shulkerBoxFile.getParentFile());
        }
        ImageIO.write(newImg, "PNG", shulkerBoxFile);
        processed.put(generic54Path.toString().replace("\\", "/"), true);
        System.out.println("processed 'generic_54.png' and saved as 'shulker_box.png'");
    }
}
