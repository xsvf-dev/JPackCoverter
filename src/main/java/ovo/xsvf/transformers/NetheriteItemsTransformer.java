package ovo.xsvf.converters;

import ovo.xsvf.api.ITransformer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;

public class NetheriteItemsTransformer implements ITransformer {
    private final List<String> itemsToCopyAndProcess = List.of("sword", "helmet", "chestplate", "leggings", "boots", "axe", "pickaxe", "shovel", "hoe");

    @Override
    public void transform(File inDir, File outDir, HashMap<String, Boolean> processed) throws IOException {
        Path itemsPathOld = Paths.get("assets", "minecraft", "textures", "items");
        Path itemsPathNew = Paths.get("assets", "minecraft", "textures", "item");

        for (String item : itemsToCopyAndProcess) {
            // 创建源文件路径和目标文件路径
            Path originalPath = itemsPathOld.resolve("diamond_" + item + ".png");
            Path newPath = itemsPathNew.resolve("netherite_" + item + ".png");

            if (Files.exists(inDir.toPath().resolve(originalPath))) {
                // 拷贝并重命名文件
                Files.copy(inDir.toPath().resolve(originalPath), outDir.toPath().resolve(newPath), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("copied and renamed 'diamond_" + item + ".png' to 'netherite_" + item + ".png'");

                // 处理元数据文件（如果存在）
                Path originalMetaPath = Path.of(originalPath + ".mcmeta");
                Path newMetaPath = Path.of(newPath + ".mcmeta");

                if (Files.exists(originalMetaPath)) {
                    Files.copy(originalMetaPath, newMetaPath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("copied and renamed 'diamond_" + item + ".png.mcmeta' to 'netherite_" + item + ".png.mcmeta'");
                }
            } else {
                System.out.println("skipping " + item + " as it is not in the list of items to copy and process");
            }
        }
    }
}
