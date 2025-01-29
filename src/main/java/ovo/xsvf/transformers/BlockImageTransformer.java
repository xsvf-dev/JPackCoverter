package ovo.xsvf.transformers;

import ovo.xsvf.ImageUtils;
import ovo.xsvf.api.ITransformer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;

public class BlockImageTransformer implements ITransformer {
    private final List<String> blocks = List.of();

    @Override
    public void transform(File inDir, File outDir, HashMap<String, Boolean> processed) throws IOException {
        Path newBlocksPath = Path.of("assets", "minecraft", "textures", "block");
        Path blocksPath = outDir.toPath().resolve(newBlocksPath);

        processAndAdjustBlockImage(blocksPath, "oak_planks.png", "warped_planks.png", 130, -23);
        processAndAdjustBlockImage(blocksPath, "oak_planks.png", "crimson_planks.png", -59, -20);
        for (String ore : List.of("coal_ore", "iron_ore", "gold_ore", "diamond_ore", "emerald_ore", "redstone_ore", "lapis_ore")) {
            processAndAdjustBlockImage(blocksPath, ore + ".png", "deepslate_" + ore + ".png", 0, -50);
        }
        processAndAdjustBlockImage(blocksPath, "redstone_ore.png", "deepslate_redstone_ore.png", 26, 0);
        processAndAdjustBlockImage(blocksPath, "coppper_ore.png", "deepslate_copper_ore.png", 0, -50);

        Path quartzPath = blocksPath.resolve("nether_quartz_ore.png");
        if (Files.exists(quartzPath)) {
            BufferedImage img = ImageIO.read(quartzPath.toFile());
            ImageUtils.changeWhiteToYellow(img);
            ImageIO.write(img, "PNG", blocksPath.resolve("nether_gold_ore.png").toFile());
            System.out.println("Processed image 'nether_gold_ore.png'");
        } else {
            System.out.println("'nether_quartz_ore.png' not found in " + blocksPath);
        }
    }

    public static void processAndAdjustBlockImage(Path path, String fileName, String newName, float hueShift, float brightnessAdjust) throws IOException {
        System.out.printf("Processing block image '%s' to '%s' with hueShift=%.2f, brightnessAdjust=%.2f%n", fileName, newName, hueShift, brightnessAdjust);

        Path originalPath = path.resolve(fileName);
        Path newPath = path.resolve(newName);

        // 检查原始文件是否存在
        if (Files.exists(originalPath)) {
            // 复制原始文件到新位置
            BufferedImage img = ImageIO.read(originalPath.toFile());
            if (hueShift != 0) {
                ImageUtils.adjustHue(img, hueShift);
            }
            if (brightnessAdjust != 0) {
                ImageUtils.adjustBrightnessForGrayscale(img, brightnessAdjust);
            }

            // 保存处理后的图像
            ImageIO.write(img, "PNG", newPath.toFile());
            System.out.printf("Processed '%s' to '%s' with hueShift=%.2f, brightnessAdjust=%.2f%n", fileName, newName, hueShift, brightnessAdjust);

            // 复制并重命名 .mcmeta 文件
            Path originalMcmetaPath = originalPath.resolveSibling(fileName + ".mcmeta");
            Path newMcmetaPath = newPath.resolveSibling(newName + ".mcmeta");
            if (Files.exists(originalMcmetaPath)) {
                Files.copy(originalMcmetaPath, newMcmetaPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.printf("Copied and renamed '%s' to '%s'%n", originalMcmetaPath, newMcmetaPath);
            }
        } else {
            System.out.printf("'%s' not found in %s%n", fileName, path);
        }
    }
}
