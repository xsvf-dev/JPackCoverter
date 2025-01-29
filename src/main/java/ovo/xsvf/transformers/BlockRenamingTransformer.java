package ovo.xsvf.transformers;

import ovo.xsvf.api.ITransformer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class BlocksTransformer implements ITransformer {
    Map<String, String> renamePairs = new HashMap<>();
    {
        renamePairs.put("stone_granite.png", "granite.png");
        renamePairs.put("stone_granite_smooth.png", "polished_granite.png");
        renamePairs.put("stone_diorite.png", "diorite.png");
        renamePairs.put("stone_diorite_smooth.png", "polished_diorite.png");
        renamePairs.put("stone_andesite.png", "andesite.png");
        renamePairs.put("stone_andesite_smooth.png", "polished_andesite.png");
        renamePairs.put("grass_side.png", "grass_block_side.png");
        renamePairs.put("grass_top.png", "grass_block_top.png");
        renamePairs.put("dirt_podzol_side.png", "podzol_side.png");
        renamePairs.put("dirt_podzol_top.png", "podzol_top.png");
        renamePairs.put("planks_acacia.png", "acacia_planks.png");
        renamePairs.put("planks_big_oak.png", "dark_oak_planks.png");
        renamePairs.put("planks_birch.png", "birch_planks.png");
        renamePairs.put("planks_jungle.png", "jungle_planks.png");
        renamePairs.put("planks_spruce.png", "spruce_planks.png");
        renamePairs.put("planks_oak.png", "oak_planks.png");
        renamePairs.put("quartz_ore.png", "nether_quartz_ore.png");
        renamePairs.put("sponge_wet.png", "wet_sponge.png");
        renamePairs.put("sandstone_normal.png", "sandstone.png");
        renamePairs.put("sandstone_carved.png", "chiseled_sandstone.png");
        renamePairs.put("sandstone_smooth.png", "cut_sandstone.png");
        renamePairs.put("red_sandstone_normal.png", "red_sandstone.png");
        renamePairs.put("red_sandstone_carved.png", "chiseled_red_sandstone.png");
        renamePairs.put("red_sandstone_smooth.png", "cut_red_sandstone.png");
        renamePairs.put("wool_colored_black.png", "black_wool.png");
        renamePairs.put("wool_colored_blue.png", "blue_wool.png");
        renamePairs.put("wool_colored_brown.png", "brown_wool.png");
        renamePairs.put("wool_colored_cyan.png", "cyan_wool.png");
        renamePairs.put("wool_colored_gray.png", "gray_wool.png");
        renamePairs.put("wool_colored_green.png", "green_wool.png");
        renamePairs.put("wool_colored_light_blue.png", "light_blue_wool.png");
        renamePairs.put("wool_colored_lime.png", "lime_wool.png");
        renamePairs.put("wool_colored_magenta.png", "magenta_wool.png");
        renamePairs.put("wool_colored_orange.png", "orange_wool.png");
        renamePairs.put("wool_colored_pink.png", "pink_wool.png");
        renamePairs.put("wool_colored_purple.png", "purple_wool.png");
        renamePairs.put("wool_colored_red.png", "red_wool.png");
        renamePairs.put("wool_colored_silver.png", "light_gray_wool.png");
        renamePairs.put("wool_colored_white.png", "white_wool.png");
        renamePairs.put("wool_colored_yellow.png", "yellow_wool.png");
        renamePairs.put("stone_slab_side.png", "smooth_stone_slab_side.png");
        renamePairs.put("stone_slab_top.png", "smooth_stone.png");
        renamePairs.put("brick.png", "bricks.png");
        renamePairs.put("nether_brick.png", "nether_bricks.png");
        renamePairs.put("stonebrick.png", "stone_bricks.png");
        renamePairs.put("stonebrick_carved.png", "chiseled_stone_bricks.png");
        renamePairs.put("stonebrick_mossy.png", "mossy_stone_bricks.png");
        renamePairs.put("quartz_block_chiseled.png", "chiseled_quartz_block.png");
        renamePairs.put("quartz_block_lines.png", "quartz_pillar.png");
        renamePairs.put("quartz_block_lines_top.png", "quartz_pillar_top.png");
        renamePairs.put("prismarine_dark.png", "dark_prismarine.png");
        renamePairs.put("prismarine_rough.png", "prismarine.png");
        renamePairs.put("prismarine_rough.png.mcmeta", "prismarine.png.mcmeta");
        renamePairs.put("anvil_base.png", "anvil.png");
        renamePairs.put("anvil_top_damage_0.png", "anvil_top.png");
        renamePairs.put("anvil_top_damage_1.png", "chipped_anvil_top.png");
        renamePairs.put("anvil_top_damage_2.png", "damaged_anvil_top.png");
        renamePairs.put("carrots_stage_0.png", "carrots_stage0.png");
        renamePairs.put("carrots_stage_1.png", "carrots_stage1.png");
        renamePairs.put("carrots_stage_2.png", "carrots_stage2.png");
        renamePairs.put("carrots_stage_3.png", "carrots_stage3.png");
        renamePairs.put("cobblestone_mossy.png", "mossy_cobblestone.png");
        renamePairs.put("cocoa_stage_0.png", "cocoa_stage0.png");
        renamePairs.put("cocoa_stage_1.png", "cocoa_stage1.png");
        renamePairs.put("cocoa_stage_2.png", "cocoa_stage2.png");
        renamePairs.put("comparator_off.png", "comparator.png");
        renamePairs.put("deadbush.png", "dead_bush.png");
        renamePairs.put("dispenser_front_horizontal.png", "dispenser_horizontal.png");
        renamePairs.put("door_acacia_lower.png", "acacia_door_bottom.png");
        renamePairs.put("door_acacia_upper.png", "acacia_door_top.png");
        renamePairs.put("door_birch_lower.png", "birch_door_bottom.png");
        renamePairs.put("door_birch_upper.png", "birch_door_top.png");
        renamePairs.put("door_dark_oak_lower.png", "dark_oak_door_bottom.png");
        renamePairs.put("door_dark_oak_upper.png", "dark_oak_door_top.png");
        renamePairs.put("door_iron_lower.png", "iron_door_bottom.png");
        renamePairs.put("door_iron_upper.png", "iron_door_top.png");
        renamePairs.put("door_jungle_lower.png", "jungle_door_bottom.png");
        renamePairs.put("door_jungle_upper.png", "jungle_door_top.png");
        renamePairs.put("door_spruce_lower.png", "spruce_door_bottom.png");
        renamePairs.put("door_spruce_upper.png", "spruce_door_top.png");
        renamePairs.put("door_wood_lower.png", "oak_door_bottom.png");
        renamePairs.put("door_wood_upper.png", "oak_door_top.png");
    }

    @Override
    public void transform(File inDir, File outDir, HashMap<String, Boolean> processed) throws IOException {
        Path oldBlocksPath = Path.of("assets", "minecraft", "textures", "blocks");
        Path newBlocksPath = Path.of("assets", "minecraft", "textures", "block");

        // first, copy the old blocks directory to the new blocks directory
        Path sourcePath = inDir.toPath().resolve(oldBlocksPath);
        Path destPath = outDir.toPath().resolve(newBlocksPath);
        Files.createDirectories(destPath);
        Files.walkFileTree(sourcePath, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path targetFile = destPath.resolve(renamePairs.getOrDefault(file.getFileName().toString(), file.getFileName().toString()));
                Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path targetDir = destPath.resolve(sourcePath.relativize(dir));
                Files.createDirectories(targetDir);
                return FileVisitResult.CONTINUE;
            }
        });

        for (var entry : processed.entrySet()) {
            if (Path.of(entry.getKey()).startsWith(oldBlocksPath)) {
                System.out.println("blocks transformer: " + entry.getKey() + ":" + entry.getValue());
                entry.setValue(true);
            }
        }
    }

    public static void processRedstoneDustCrossImage(String blocksPathNew) {
        log("Processing redstone dust cross image in: " + blocksPathNew);

        // redstone_dust_cross.png 文件路径
        File redstoneDustCrossFile = new File(blocksPathNew, "redstone_dust_cross.png");

        if (redstoneDustCrossFile.exists()) {
            try {
                // 读取图片
                BufferedImage img = ImageIO.read(redstoneDustCrossFile);

                // 检查图像是否是 16x16
                if (img.getWidth() == 16 && img.getHeight() == 16) {
                    // 遍历每个像素并修改
                    for (int x = 0; x < 16; x++) {
                        for (int y = 0; y < 16; y++) {
                            // 保留交叉的红石尘土部分
                            if (!((x == y && 5 <= x && x <= 11) || (x + y == 16 && 5 <= x && x <= 11))) {
                                img.setRGB(x, y, 0x00000000); // 设置为透明 (Alpha 0)
                            }
                        }
                    }
                    // 保存新文件
                    File newFile = new File(blocksPathNew, "red_dust_dot.png");
                    ImageIO.write(img, "PNG", newFile);
                    log("Processed and renamed 'redstone_dust_cross.png' to 'red_dust_dot.png'");
                } else {
                    log("'redstone_dust_cross.png' is not a 16x16 image");
                }
            } catch (IOException e) {
                log("Error processing 'redstone_dust_cross.png': " + e.getMessage());
            }
        } else {
            log("'redstone_dust_cross.png' not found in " + blocksPathNew);
        }
    }
}
