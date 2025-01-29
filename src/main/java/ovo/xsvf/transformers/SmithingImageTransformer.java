package ovo.xsvf.transformers;

import ovo.xsvf.ImageUtils;
import ovo.xsvf.api.ITransformer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class SmithingImageTransformer implements ITransformer {
    @Override
    public void transform(File inDir, File outDir, HashMap<String, Boolean> processed) throws IOException {
        Path smithingPath = Path.of("assets", "minecraft", "textures", "gui", "container", "anvil.png");

        if (Files.exists(inDir.toPath().resolve(smithingPath))) {
            BufferedImage img = ImageIO.read(inDir.toPath().resolve(smithingPath).toFile());
            int width = img.getWidth();
            int height = img.getHeight();

            // 确定缩放因子
            int scaleFactor;
            if (width == 256 && height == 256) {
                scaleFactor = 1;
            } else if (width == 512 && height == 512) {
                scaleFactor = 2;
            } else if (width == 1024 && height == 1024) {
                scaleFactor = 4;
            } else if (width == 2048 && height == 2048) {
                scaleFactor = 8;
            } else {
                System.out.println("Unsupported image size for 'smithing.png': " + width + "x" + height);
                return;
            }

            // 移动箭头区域
            ImageUtils.moveRegion(img, 103 * scaleFactor, 33 * scaleFactor, 121 * scaleFactor, 42 * scaleFactor, -25 * scaleFactor, 0);
            // 移动加号区域
            ImageUtils.moveRegion(img, 76 * scaleFactor, 33 * scaleFactor, 85 * scaleFactor, 42 * scaleFactor, -25 * scaleFactor, 0);
            // 移动输出槽区
            ImageUtils.moveRegion(img, 134 * scaleFactor, 31 * scaleFactor, 152 * scaleFactor, 49 * scaleFactor, -25 * scaleFactor, 0);
            // 移动输入槽区域
            ImageUtils.moveRegion(img, 44 * scaleFactor, 31 * scaleFactor, 62 * scaleFactor, 49 * scaleFactor, -25 * scaleFactor, 0);
            // 移动升级槽区域
            ImageUtils.moveRegion(img, 89 * scaleFactor, 31 * scaleFactor, 107 * scaleFactor, 49 * scaleFactor, -25 * scaleFactor, 0);

            // 保存处理后的图像
            ImageIO.write(img, "PNG", outDir.toPath().resolve(smithingPath).toFile());
            processed.put(smithingPath.toString().replace("\\", "/"), true);
            System.out.println("Processed 'smithing.png' in " + inDir.toPath().resolve(smithingPath));

        } else {
            System.out.println("No 'smithing.png' found, skipping!");
        }
    }
}
