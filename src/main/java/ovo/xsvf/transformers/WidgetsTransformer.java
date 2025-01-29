package ovo.xsvf.converters;

import ovo.xsvf.api.ITransformer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

public class WidgetsTransformer implements ITransformer {
    @Override
    public void transform(File inDir, File outDir, HashMap<String, Boolean> processed) throws IOException {
        Path widgetsPath = Path.of("assets", "minecraft", "textures", "gui", "widgets.png");
        File input = inDir.toPath().resolve(widgetsPath).toFile();
        if (!input.exists()) {
            System.out.println("widgets.png not found, skipping!");
            return;
        }

        BufferedImage img = ImageIO.read(input);
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
            System.out.println("Unsupported image size: " + width + "x" + height);
            return;
        }

        Rectangle sourceBox = scaledCoords(1, 1, 21, 21, scaleFactor);
        Rectangle destBox1 = scaledCoords(25, 24, 45, 44, scaleFactor);
        Rectangle destBox2 = scaledCoords(61, 24, 81, 44, scaleFactor);

        BufferedImage region = img.getSubimage(sourceBox.x, sourceBox.y, sourceBox.width, sourceBox.height);
        Graphics2D g2d = img.createGraphics();

        g2d.drawImage(region, destBox1.x, destBox1.y, null);
        g2d.drawImage(region, destBox2.x, destBox2.y, null);
        g2d.dispose();

        File output = outDir.toPath().resolve(widgetsPath).toFile();
        if (!output.getParentFile().exists() && !output.getParentFile().mkdirs()) {
            throw new IOException("Failed to create output directory: " + output.getParentFile());
        }
        ImageIO.write(img, "PNG", output);
        processed.put(widgetsPath.toString().replace("\\", "/"), true);
        System.out.println("processed widgets.png.");
    }

    // 计算缩放后的坐标
    private static Rectangle scaledCoords(int x1, int y1, int x2, int y2, int scaleFactor) {
        return new Rectangle(x1 * scaleFactor, y1 * scaleFactor, (x2 - x1) * scaleFactor, (y2 - y1) * scaleFactor);
    }
}
