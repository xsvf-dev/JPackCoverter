package ovo.xsvf.transformers;

import ovo.xsvf.FileUtils;
import ovo.xsvf.ImageUtils;
import ovo.xsvf.api.ITransformer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

public class ClockTransformer implements ITransformer {
    @Override
    public void transform(File inDir, File outDir, HashMap<String, Boolean> processed) throws IOException {
        Path oldItemsPath = Path.of("assets", "minecraft", "textures", "items");
        Path newItemsPath = Path.of("assets", "minecraft", "textures", "item");
        ImageUtils.splitImage(inDir.toPath().resolve(oldItemsPath.resolve("clock.png")), outDir.toPath().resolve(newItemsPath), "clock", 64);
        FileUtils.deleteFile(outDir.toPath().resolve(newItemsPath.resolve("clock.png")));
        processed.put(oldItemsPath.resolve("clock.png").toString().replace("\\", "/"), true);
    }
}
