package ovo.xsvf.transformers;

import ovo.xsvf.FileUtils;
import ovo.xsvf.ImageUtils;
import ovo.xsvf.api.ITransformer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class RedstoneTransformer implements ITransformer {
    @Override
    public void transform(File inDir, File outDir, HashMap<String, Boolean> processed) throws IOException {
        Path newBlocksPath = Path.of("assets", "minecraft", "textures", "block");
        File newBlocksFile = outDir.toPath().resolve(newBlocksPath).toFile();

        File redstoneDustCrossFile = new File(newBlocksFile, "redstone_dust_cross.png");
        if (redstoneDustCrossFile.exists()) {
            BufferedImage img = ImageIO.read(redstoneDustCrossFile);
            if (img.getWidth() == 16 && img.getHeight() == 16) {
                for (int x = 0; x < 16; x++) {
                    for (int y = 0; y < 16; y++) {
                        if (!((x == y && 5 <= x && x <= 11) || (x + y == 16 && 5 <= x && x <= 11))) {
                            img.setRGB(x, y, 0);
                        }
                    }
                }
                File newFile = new File(newBlocksFile, "red_dust_dot.png");
                ImageIO.write(img, "PNG", newFile);
                FileUtils.deleteFile(redstoneDustCrossFile.toPath());
                System.out.println("Processed and renamed 'redstone_dust_cross.png' to 'red_dust_dot.png'");
            } else {
                System.out.println("'redstone_dust_cross.png' is not a 16x16 image");
            }
        } else {
            System.out.println("'redstone_dust_cross.png' not found in " + newBlocksFile.getAbsolutePath());
        }

        Path redstoneDustLinePath = newBlocksFile.toPath().resolve("redstone_dust_line.png");
        if (Files.exists(redstoneDustLinePath)) {
            BufferedImage img = ImageIO.read(redstoneDustLinePath.toFile());

            // Rotate the image 90 degrees clockwise
            BufferedImage rotated90cw = ImageUtils.rotateImage(img, -90);
            // Rotate the image 90 degrees counterclockwise
            BufferedImage rotated90ccw = ImageUtils.rotateImage(img, 90);

            File line0File = new File(newBlocksFile, "redstone_dust_line0.png");
            File line1File = new File(newBlocksFile, "redstone_dust_line1.png");

            ImageIO.write(rotated90cw, "PNG", line0File);
            ImageIO.write(rotated90ccw, "PNG", line1File);

            System.out.println("Processed 'redstone_dust_line.png' to 'redstone_dust_line0.png' and 'redstone_dust_line1.png'");
        } else {
            System.out.println("'redstone_dust_line.png' not found in " + newBlocksFile.getAbsolutePath());
        }
    }
}
