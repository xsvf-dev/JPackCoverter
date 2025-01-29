package ovo.xsvf.converters;

import org.apache.commons.io.IOUtils;
import ovo.xsvf.FileUtils;
import ovo.xsvf.api.ITransformer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

public class FurnaceTransformer implements ITransformer {
    @Override
    public void transform(File inDir, File outDir, HashMap<String, Boolean> processed) throws IOException {
        // container_path = os.path.join(assets_path, 'gui', 'container')
        Path containerPath = Path.of("assets", "minecraft", "textures", "gui", "container");

        Path blastFurnacePath = containerPath.resolve("blast_furnace.png");
        Path smokerPath = containerPath.resolve("smoker.png");
        Path furnacePath = containerPath.resolve("furnace.png");

        File inFile = inDir.toPath().resolve(furnacePath).toFile();
        if (!inFile.exists()) {
            System.out.println("furnace.png not found, skipping!");
            return;
        }

        File containerFile = outDir.toPath().resolve(containerPath).toFile();
        File blastFurnaceOutFile = outDir.toPath().resolve(blastFurnacePath).toFile();
        File smokerOutFile = outDir.toPath().resolve(smokerPath).toFile();
        if (!FileUtils.checkAndCreateParentDir(containerFile)) {
            throw new IOException("Failed to create container directory: " + containerFile.getAbsolutePath());
        }

        try (FileInputStream in = new FileInputStream(inFile);
             FileInputStream in2 = new FileInputStream(inFile);
             FileOutputStream outBlastFurnace = new FileOutputStream(blastFurnaceOutFile);
             FileOutputStream outSmoker = new FileOutputStream(smokerOutFile)) {
            IOUtils.copy(in, outBlastFurnace);
            IOUtils.copy(in2, outSmoker);
        }
        processed.put(furnacePath.toString().replace("\\", "/"), true);
        System.out.println("copied furnace.png -> blast_furnace.png & smoker.png");
    }
}
