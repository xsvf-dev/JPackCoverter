package ovo.xsvf.transformers;

import ovo.xsvf.api.ITransformer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class EnchantedItemGlintTransformer implements ITransformer {
    @Override
    public void transform(File inDir, File outDir, HashMap<String, Boolean> processed) throws IOException {
        Path miscPath = Paths.get("assets", "minecraft", "textures", "misc");
        processed.put(miscPath.resolve("enchanted_item_glint.png").toString().replace("\\", "/"), true); // delete enchanted_item_glint.png in misc_path
        processed.put(miscPath.resolve("enchanted_item_glint.png.mcmeta").toString().replace("\\", "/"), true);
    }
}
