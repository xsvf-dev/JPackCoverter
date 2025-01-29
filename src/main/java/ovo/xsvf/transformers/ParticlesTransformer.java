package ovo.xsvf.transformers;

import ovo.xsvf.FileUtils;
import ovo.xsvf.api.ITransformer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

public class ParticlesTransformer implements ITransformer {
    @Override
    public void transform(File inDir, File outDir, HashMap<String, Boolean> processed) throws IOException {
        Path particlePath = Path.of("assets", "minecraft", "textures", "particle");
        Path particlesFile = inDir.toPath().resolve(particlePath).resolve("particles.png");
        if (!particlesFile.toFile().exists()) {
            System.out.println("Particle file not found.");
            return;
        }

        BufferedImage img = ImageIO.read(particlesFile.toFile());
        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();

        if (imgWidth != imgHeight || imgWidth % 16 != 0) {
            throw new IllegalArgumentException("'particles.png' is not a square image with a size multiple of 16: " + imgWidth + "x" + imgHeight);
        }

        int splitSize = imgWidth / 16;
        File outputDirParticle = outDir.toPath().resolve(particlePath).toFile();
        File outputDirEntity = outDir.toPath().resolve(Path.of("assets", "minecraft", "textures", "entity")).toFile();
        FileUtils.checkAndCreateParentDir(outputDirParticle);
        FileUtils.checkAndCreateParentDir(outputDirEntity);

        for (int row = 0; row < 16; row++) {
            for (int col = 0; col < 16; col++) {
                // Crop the image into 16x16 parts
                BufferedImage splitImg = img.getSubimage(col * splitSize, row * splitSize, splitSize, splitSize);
                String outputPath = "";

                // Conditions for saving different particle types
                if (row == 0 && col < 8) {
                    outputPath = new File(outputDirParticle, String.format("generic_%d.png", col)).getAbsolutePath();
                } else if (row == 1 && col >= 3 && col <= 6) {
                    outputPath = new File(outputDirParticle, String.format("splash_%d.png", col - 3)).getAbsolutePath();
                } else if (row == 2 && col == 0) {
                    outputPath = new File(outputDirParticle, "bubble.png").getAbsolutePath();
                } else if (row == 2 && col == 1) {
                    outputPath = new File(outputDirEntity, "fishing_hook.png").getAbsolutePath();
                } else if (row == 3 && col == 0) {
                    outputPath = new File(outputDirParticle, "flame.png").getAbsolutePath();
                } else if (row == 3 && col == 1) {
                    outputPath = new File(outputDirParticle, "lava.png").getAbsolutePath();
                } else if (row == 4 && col < 3) {
                    outputPath = new File(outputDirParticle, new String[]{"note.png", "critical_hit.png", "enchanted_hit.png"}[col]).getAbsolutePath();
                } else if (row == 5 && col < 3) {
                    outputPath = new File(outputDirParticle, new String[]{"heart.png", "angry.png", "glint.png"}[col]).getAbsolutePath();
                } else if (row == 7 && col < 3) {
                    outputPath = new File(outputDirParticle, new String[]{"drip_hang.png", "drip_fall.png", "drip_land.png"}[col]).getAbsolutePath();
                } else if (row == 8 && col < 8) {
                    outputPath = new File(outputDirParticle, String.format("effect_%d.png", col)).getAbsolutePath();
                } else if (row == 9 && col < 8) {
                    outputPath = new File(outputDirParticle, String.format("spell_%d.png", col)).getAbsolutePath();
                } else if (row == 10 && col < 8) {
                    outputPath = new File(outputDirParticle, String.format("spark_%d.png", col)).getAbsolutePath();
                }

                if (!outputPath.isEmpty()) {
                    // Save the cropped image as a PNG file
                    File outputFile = new File(outputPath);
                    FileUtils.checkAndCreateParentDir(outputFile);
                    ImageIO.write(splitImg, "PNG", outputFile);
                }
            }
        }
        processed.put(particlesFile.toString().replace("\\", "/"), true);
    }
}
