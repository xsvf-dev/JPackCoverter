package ovo.xsvf.converters;

import ovo.xsvf.FileUtils;
import ovo.xsvf.api.ITransformer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

public class ItemsPathTransformer implements ITransformer {
    @Override
    public void transform(File inDir, File outDir, HashMap<String, Boolean> processed) throws IOException {
        Path itemsPathOld = Path.of("assets", "minecraft", "textures", "items");
        Path itemsPathNew = Path.of("assets", "minecraft", "textures", "item");
        FileUtils.copyDirectory(inDir.toPath().resolve(itemsPathOld).toFile(),
                outDir.toPath().resolve(itemsPathNew).toFile());
        processed.entrySet().forEach(entry ->  {
            if (entry.getKey().startsWith(itemsPathOld.toString().replace("\\", "/"))) {
                System.out.println("set to processed: " + entry.getKey());
                entry.setValue(true);
            }
        });
    }
}
