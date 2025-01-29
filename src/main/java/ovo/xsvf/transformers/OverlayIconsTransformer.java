package ovo.xsvf.converters;

import ovo.xsvf.api.ITransformer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

public class OverlayIconsTransformer implements ITransformer {
    @Override
    public void transform(File inDir, File outDir, HashMap<String, Boolean> fileMap) throws IOException {
        // TODO: fix this
//        File iconsFile = Path.of(inDir.getAbsolutePath(), "assets", "minecraft", "textures", "gui", "icons.png").toFile();
//        if (!iconsFile.exists()) {
//            System.out.println("icons.png not found. skipping!");
//            return;
//        }
//        BufferedImage image = ImageIO.read(iconsFile);
//        if (!(image.getWidth() == image.getHeight())) {
//            System.out.println("icons.png is not a square image. skipping!");
//            return;
//        }
//        File outputFile = Path.of(outDir.getAbsolutePath(), "icons.png").toFile();
    }
}
