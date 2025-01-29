package ovo.xsvf.transformers;

import ovo.xsvf.api.ITransformer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

public class CreativeInventoryImageTransformer implements ITransformer {
    @Override
    public void transform(File inDir, File outDir, HashMap<String, Boolean> processed) throws IOException {
        // creative_inventory_path = os.path.join(assets_path, 'gui', 'container', 'creative_inventory', 'tab_inventory.png')
        File file = Path.of(inDir.getAbsolutePath(), "assets", "minecraft", "textures", "gui", "container", "creative_inventory", "tab_inventory.png").toFile();
        if (!file.exists()) {
            System.out.println("creative inventory image not found, skipping!");
            return;
        }
        BufferedImage img = ImageIO.read(file);

        int width = img.getWidth();
        int height = img.getHeight();

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
            System.out.println("Unsupported image size for 'tab_inventory.png': " + width + "x" + height);
            return;
        }

        Rectangle sourceBox = scaledCoords(6, 0, 84, 53, scaleFactor);
        Rectangle destBox = scaledCoords(51, 0, 129, 53, scaleFactor);

        BufferedImage region = img.getSubimage(sourceBox.x, sourceBox.y, sourceBox.width, sourceBox.height);
        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(region, destBox.x, destBox.y, null);

        Rectangle fillBox = scaledCoords(6, 0, 53, 53, scaleFactor);
        Color fillColor = new Color(img.getRGB(164 * scaleFactor, 27 * scaleFactor));
        for (int x = fillBox.x; x < fillBox.x + fillBox.width; x++) {
            for (int y = fillBox.y; y < fillBox.y + fillBox.height; y++) {
                img.setRGB(x, y, fillColor.getRGB());
            }
        }

        BufferedImage region18x18 = img.getSubimage(53 * scaleFactor, 5 * scaleFactor, 18 * scaleFactor, 18 * scaleFactor);
        g2d.drawImage(region18x18, 34 * scaleFactor, 19 * scaleFactor, null);
        g2d.dispose();

        // 保存修改后的图像
        File outFile = Path.of(outDir.getAbsolutePath(), "assets", "minecraft", "textures", "gui", "container", "creative_inventory", "tab_inventory.png").toFile();
        if (!outFile.getParentFile().exists() && !outFile.getParentFile().mkdirs()) {
            throw new IOException("Failed to create output directory: " + outFile.getParentFile());
        }
        processed.put("assets/minecraft/textures/gui/container/creative_inventory/tab_inventory.png", true);
        ImageIO.write(img, "PNG", outFile);
    }

    // 计算缩放后的坐标
    public static Rectangle scaledCoords(int x1, int y1, int x2, int y2, int scaleFactor) {
        return new Rectangle(x1 * scaleFactor, y1 * scaleFactor, (x2 - x1) * scaleFactor, (y2 - y1) * scaleFactor);
    }
}
