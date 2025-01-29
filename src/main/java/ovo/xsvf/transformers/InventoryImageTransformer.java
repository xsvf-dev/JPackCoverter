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

public class InventoryImageTransformer implements ITransformer {
    @Override
    public void transform(File inDir, File outDir, HashMap<String, Boolean> processed) throws IOException {
        Path containerPath = Path.of("assets", "minecraft", "textures", "gui", "container", "inventory.png");

        if (Files.exists(inDir.toPath().resolve(containerPath))) {
            BufferedImage img = ImageIO.read(inDir.toPath().resolve(containerPath).toFile());
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
                System.out.println("Unsupported image size for 'inventory.png': " + width + "x" + height);
                return;
            }

            // 移动区域
            ImageUtils.moveRegion(img, 86 * scaleFactor, 24 * scaleFactor, 162 * scaleFactor, 62 * scaleFactor, 10 * scaleFactor, -8 * scaleFactor);

            // 颜色填充区域
            int fillColor = img.getRGB(90 * scaleFactor, 10 * scaleFactor);
            ImageUtils.colorFillRegion(img, 75 * scaleFactor, 6 * scaleFactor, 96 * scaleFactor, 80 * scaleFactor, fillColor);
            ImageUtils.colorFillRegion(img, 96 * scaleFactor, 54 * scaleFactor, 161 * scaleFactor, 62 * scaleFactor, fillColor);

            // 复制和粘贴区域
            ImageUtils.copyAndPasteRegion(img, 152 * scaleFactor, 26 * scaleFactor, 172 * scaleFactor, 46 * scaleFactor, 75 * scaleFactor, 60 * scaleFactor);

            // 保存处理后的图像
            ImageIO.write(img, "PNG", outDir.toPath().resolve(containerPath).toFile());
            processed.put(containerPath.toString().replace("\\", "/"), true);
            System.out.println("Processed 'inventory.png' in " + containerPath);

        } else {
            System.out.println("No 'inventory.png' found in " + containerPath);
        }
    }
}
