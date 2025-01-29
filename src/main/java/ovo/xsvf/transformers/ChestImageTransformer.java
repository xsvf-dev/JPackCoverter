package ovo.xsvf.transformers;

import ovo.xsvf.FileUtils;
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
import java.util.Map;

public class ChestImageTransformer implements ITransformer {
    private static final Map<Dimension, Integer> scaleFactorMap = Map.of(
            new Dimension(64, 64), 1,
            new Dimension(128, 128), 2,
            new Dimension(256, 256), 4,
            new Dimension(512, 512), 8,
            new Dimension(1024, 1024), 16
    );

    @Override
    public void transform(File inDir, File outDir, HashMap<String, Boolean> processed) throws IOException {
        Path chestPath = Paths.get("assets", "minecraft", "textures", "entity", "chest");

        System.out.println("Processing chest images in: " + chestPath);
        for (String chestFile : new String[]{"ender.png", "normal.png", "trapped.png"}) {
            Path chestFilePath = inDir.toPath().resolve(chestPath).resolve(chestFile);
            if (!Files.exists(chestFilePath)) {
                System.out.println("'" + chestFile + "' not found in " + chestPath);
                continue;
            }

            try {
                BufferedImage img = ImageIO.read(chestFilePath.toFile());
                Dimension imgDimension = new Dimension(img.getWidth(), img.getHeight());
                Integer scaleFactor = scaleFactorMap.get(imgDimension);

                if (scaleFactor == null) {
                    System.out.println("Unsupported image size for '" + chestFile + "': " + imgDimension.width + "x" + imgDimension.height);
                    continue;
                }

                // 处理需要交换和镜像的区域
                Rectangle[][] swapPairs = {
                        {new Rectangle(14 * scaleFactor, 0, 14 * scaleFactor, 14 * scaleFactor),
                         new Rectangle(28 * scaleFactor, 0, 14 * scaleFactor, 14 * scaleFactor)},
                        {new Rectangle(14 * scaleFactor, 14 * scaleFactor, 14 * scaleFactor, 5 * scaleFactor),
                         new Rectangle(42 * scaleFactor, 14 * scaleFactor, 14 * scaleFactor, 5 * scaleFactor)},
                        {new Rectangle(14 * scaleFactor, 19 * scaleFactor, 14 * scaleFactor, 14 * scaleFactor),
                         new Rectangle(28 * scaleFactor, 19 * scaleFactor, 14 * scaleFactor, 14 * scaleFactor)},
                        {new Rectangle(14 * scaleFactor, 33 * scaleFactor, 14 * scaleFactor, 10 * scaleFactor),
                         new Rectangle(42 * scaleFactor, 33 * scaleFactor, 14 * scaleFactor, 10 * scaleFactor)}
                };

                // 执行交换和镜像操作
                for (Rectangle[] pair : swapPairs) {
                    try {
                        ImageUtils.swapAndMirror(img, pair[0], pair[1]);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error processing swap and mirror for '" + chestFile + "': " + e.getMessage());
                    }
                }

                // 额外需要镜像的区域
                Rectangle[] mirrorBoxes = {
                        new Rectangle(14 * scaleFactor, 0, 14 * scaleFactor, 14 * scaleFactor),
                        new Rectangle(28 * scaleFactor, 0, 14 * scaleFactor, 14 * scaleFactor),
                        new Rectangle(0, 14 * scaleFactor, 14 * scaleFactor, 5 * scaleFactor),
                        new Rectangle(28 * scaleFactor, 14 * scaleFactor, 14 * scaleFactor, 5 * scaleFactor),
                        new Rectangle(14 * scaleFactor, 19 * scaleFactor, 14 * scaleFactor, 14 * scaleFactor),
                        new Rectangle(28 * scaleFactor, 19 * scaleFactor, 14 * scaleFactor, 14 * scaleFactor),
                        new Rectangle(0, 33 * scaleFactor, 14 * scaleFactor, 10 * scaleFactor),
                        new Rectangle(28 * scaleFactor, 33 * scaleFactor, 14 * scaleFactor, 10 * scaleFactor)
                };

                // 对额外区域进行镜像处理
                for (Rectangle box : mirrorBoxes) {
                    try {
                        BufferedImage region = img.getSubimage(box.x, box.y, box.width, box.height);
                        BufferedImage mirroredRegion = mirrorImage(region);
                        Graphics2D g2d = img.createGraphics();
                        g2d.drawImage(mirroredRegion, box.x, box.y, null);
                        g2d.dispose();
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error mirroring box for '" + chestFile + "': " + e.getMessage());
                    }
                }

                // 保存处理后的图像
                File file = outDir.toPath().resolve(chestPath).resolve(chestFile).toFile();
                FileUtils.checkAndCreateParentDir(file);
                ImageIO.write(img, "PNG", file);
                processed.put(chestPath.resolve(chestFile).toString().replace("\\", "/"), true);
                System.out.println("Processed '" + chestFile + "' and swapped and mirrored specified regions.");

            } catch (IOException e) {
                System.err.println("Error reading or writing '" + chestFile + "': " + e.getMessage());
            }
        }
    }

    // 辅助方法：镜像单个区域
    private static BufferedImage mirrorImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // 水平翻转
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                result.setRGB(width - 1 - x, y, rgb);
            }
        }

        return result;
    }
}
