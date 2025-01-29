package ovo.xsvf.transformers;

import ovo.xsvf.ImageUtils;
import ovo.xsvf.api.ITransformer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class GrindStoneTransformer implements ITransformer {
    @Override
    public void transform(File inDir, File outDir, HashMap<String, Boolean> processed) throws IOException {
        Path containerPath = Path.of("assets", "minecraft", "textures", "gui", "container");

        Path shulkerBoxPath = inDir.toPath().resolve(containerPath).resolve("shulker_box.png");
        Path grindstonePath = outDir.toPath().resolve(containerPath).resolve("grindstone.png");
        Path grindstoneImagePath;

        if (!Files.exists(shulkerBoxPath)) {
            System.out.println("shulker_box.png not found, skipping...");
            return;
        }
        BufferedImage img = ImageIO.read(shulkerBoxPath.toFile());
        int width = img.getWidth();
        int height = img.getHeight();

        int scaleFactor;
        if (width == 256 && height == 256) {
            scaleFactor = 1;
            grindstoneImagePath = Paths.get("grindstone", "grindstone_256.png");
        } else if (width == 512 && height == 512) {
            scaleFactor = 2;
            grindstoneImagePath = Paths.get("grindstone", "grindstone_512.png");
        } else if (width == 1024 && height == 1024) {
            scaleFactor = 4;
            grindstoneImagePath = Paths.get("grindstone", "grindstone_1024.png");
        } else if (width == 2048 && height == 2048) {
            scaleFactor = 8;
            grindstoneImagePath = Paths.get("grindstone", "grindstone_2048.png");
        } else {
            throw new IllegalArgumentException("Invalid shulker_box.png size: " + width + "x" + height);
        }

        BufferedImage imgCopy = ImageUtils.deepCopy(img);
        Color fillColor = new Color(img.getRGB(5 * scaleFactor, 4 * scaleFactor));

        // Define the region to cover
        Rectangle coverBox = new Rectangle(7 * scaleFactor, 17 * scaleFactor, 169 * scaleFactor, 71 * scaleFactor);
        for (int x = coverBox.x; x < coverBox.x + coverBox.width; x++) {
            for (int y = coverBox.y; y < coverBox.y + coverBox.height; y++) {
                imgCopy.setRGB(x, y, fillColor.getRGB());
            }
        }

        // Copy and paste region
        BufferedImage region = imgCopy.getSubimage(7 * scaleFactor, 83 * scaleFactor, 18 * scaleFactor, 18 * scaleFactor);
        imgCopy.getGraphics().drawImage(region, 48 * scaleFactor, 18 * scaleFactor, null);
        imgCopy.getGraphics().drawImage(region, 128 * scaleFactor, 33 * scaleFactor, null);
        imgCopy.getGraphics().drawImage(region, 48 * scaleFactor, 39 * scaleFactor, null);

        if (Files.exists(grindstoneImagePath)) {
            BufferedImage overlayImg = ImageIO.read(grindstoneImagePath.toFile());
            Graphics2D g2d = imgCopy.createGraphics();
            g2d.drawImage(overlayImg, 0, 0, null);
            g2d.dispose();

            ImageIO.write(imgCopy, "PNG", grindstonePath.toFile());
            System.out.println("Processed 'shulker_box.png' and saved as 'grindstone.png'");
        } else {
            System.out.println("No overlay image found for size " + width + "x" + height);
        }
    }
}
