package ovo.xsvf.transformers;

import ovo.xsvf.api.ITransformer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class BlcokStatesTransformer implements ITransformer {
    @Override
    public void transform(File inDir, File outDir, HashMap<String, Boolean> processed) throws IOException {
        processed.entrySet().forEach(entry -> {
            if (entry.getKey().contains("/assets/minecraft/blockstates/")) {
                System.out.println("found blockstate file: " + entry.getKey());
                entry.setValue(true); // delete the blockstate files
            }
        });
    }
}
