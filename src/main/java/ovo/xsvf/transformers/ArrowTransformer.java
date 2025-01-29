package ovo.xsvf.transformers;

import ovo.xsvf.FileUtils;
import ovo.xsvf.api.ITransformer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class ArrowTransformer implements ITransformer {
    @Override
    public void transform(File inDir, File outDir, HashMap<String, Boolean> processed) throws IOException {
        Path oldItemsPath = Path.of("assets", "minecraft", "textures", "items");
        Path newItemsPath = Path.of("assets", "minecraft", "textures", "item");

        Path oldArrowPath = oldItemsPath.resolve("arrow.png");
        Path newArrowPath = newItemsPath.resolve("tipped_arrow_base.png");

        File oldArrowFile = inDir.toPath().resolve(oldArrowPath).toFile();
        File newArrowFile = outDir.toPath().resolve(newArrowPath).toFile();
        FileUtils.checkAndCreateParentDir(newArrowFile);
        if (!oldArrowFile.exists()) {
            System.out.println("arrow image not found.skipping!");
            return;
        }

        BufferedImage img = ImageIO.read(oldArrowFile);
        if (img.getWidth() == 16 && img.getHeight() == 16) {
            // Make the top-right 5x5 pixels transparent
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    img.setRGB(11 + i, j, 0);  // Set pixel to transparent
                }
            }
            ImageIO.write(img, "PNG", newArrowFile);
            processed.put(oldArrowPath.toString().replace("\\", "/"), true);
        } else {
            System.out.println("arrow image is not 16x16 pixels, skipping!");
        }
    }
}
