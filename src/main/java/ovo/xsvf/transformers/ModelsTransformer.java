package ovo.xsvf.converters;

import ovo.xsvf.api.ITransformer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ModelsTransformer implements ITransformer {
    @Override
    public void transform(File inDir, File outDir, HashMap<String, Boolean> processed) throws IOException {
        //models_path = os.path.join(temp_dir, 'assets', 'minecraft', 'models')
        processed.entrySet().forEach(entry -> {
            if (entry.getKey().contains("/assets/minecraft/models/")) {
                System.out.println("found model file: " + entry.getKey());
                entry.setValue(true); // mark as processed to delete it
            }
        });
    }
}
