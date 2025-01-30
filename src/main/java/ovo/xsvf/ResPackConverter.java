package ovo.xsvf;

import lombok.Getter;
import ovo.xsvf.api.IConverter;
import ovo.xsvf.api.ITransformer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Getter
public class ResPackConverter implements IConverter {
    private final File input;
    private final File output;
    private final List<ITransformer> transformers = new ArrayList<>();

    public ResPackConverter(File input, File output, ITransformer... transformers) {
        this.input = input;
        this.output = output;
        this.transformers.addAll(List.of(transformers));
    }

    @Override
    public void convert() throws IOException {
        System.out.println("coverting....");
        // first, create temp dir
        File outDir = new File(output.getParentFile(), "temp_out_" + System.currentTimeMillis());
        if (!outDir.mkdirs()) {
            throw new IOException("Failed to create temp dir: " + outDir.getAbsolutePath());
        }

        File inDir = new File(output.getParentFile(), "temp_in_" + System.currentTimeMillis());
        if (!inDir.mkdirs()) {
            throw new IOException("Failed to create temp dir: " + inDir.getAbsolutePath());
        }

        // create a hashmap of all files in the input.zip
        HashMap<String, Boolean> fileMap = new HashMap<>();
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(input))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (entry.isDirectory()) continue;
                File file = new File(inDir, entry.getName());
                if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                    throw new IOException("Failed to create dir: " + file.getAbsolutePath());
                }
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(zipInputStream.readAllBytes());
                fos.close();
                fileMap.put(entry.getName(), false);
            }
        }

        // then, transform the files
        for (ITransformer transformer : transformers) {
            System.out.println("Running transformer: " + transformer.getClass().getSimpleName());
            transformer.transform(inDir, outDir, fileMap);
        }

        // finally, create the output zip
        Path out = outDir.toPath();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(output))) {
            Files.walkFileTree(out, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    String string = out.relativize(file).toString().replace("\\", "/");
                    fileMap.put(string, true);
                    zipOutputStream.putNextEntry(new ZipEntry(string));
                    zipOutputStream.write(Files.readAllBytes(file));
                    zipOutputStream.closeEntry();
                    return FileVisitResult.CONTINUE;
                }
            });
            Files.walkFileTree(inDir.toPath(), new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    String string = inDir.toPath().relativize(file).toString().replace("\\", "/");
                    if (fileMap.getOrDefault(string, false)) {
                        System.out.println("Skipping file: " + string);
                        return FileVisitResult.CONTINUE;
                    }
                    zipOutputStream.putNextEntry(new ZipEntry(string));
                    zipOutputStream.write(Files.readAllBytes(file));
                    zipOutputStream.closeEntry();
                    return FileVisitResult.CONTINUE;
                }
            });
        }

        // wait for a bit to allow the zip to be written
        try {
            Thread.sleep(10);
        } catch (InterruptedException ignored) {}

        FileUtils.delete(inDir);
        FileUtils.delete(outDir);

        System.out.println("Done!");
    }
}

