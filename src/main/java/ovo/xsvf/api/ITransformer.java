package ovo.xsvf.api;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public interface ITransformer {
    void transform(File inDir, File outDir, HashMap<String, Boolean> processed) throws IOException;
}
