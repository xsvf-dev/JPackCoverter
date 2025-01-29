package ovo.xsvf.transformers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import ovo.xsvf.api.ITransformer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class PackInfoTransformer implements ITransformer {
    @Override
    public void transform(File inDir, File outDir, HashMap<String, Boolean> processed) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Path path = Path.of("pack.mcmeta");
        Path packInfoPath = inDir.toPath().resolve(path);
        if (!inDir.toPath().resolve(path).toFile().exists()) {
            throw new IOException("pack.mcmeta not found in input directory");
        }
        JsonObject packInfo = gson.fromJson(Files.readString(packInfoPath), JsonObject.class);
        packInfo.get("pack").getAsJsonObject().entrySet().forEach(entry -> {
            if (entry.getKey().equals("pack_format")) {
                entry.setValue(new JsonPrimitive(8)); // 1.18
            }
        });
        Files.writeString(outDir.toPath().resolve(path), gson.toJson(packInfo));
        processed.put(path.toString().replace("\\", "/"), true);
    }
}
