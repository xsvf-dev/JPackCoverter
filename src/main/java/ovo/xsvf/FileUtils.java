package ovo.xsvf;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FileUtils {
    public static boolean checkAndCreateParentDir(File file) {
        if (!file.getParentFile().exists()) {
            return file.getParentFile().mkdirs();
        } else return true;
    }

    public static boolean copyFile(Path source, Path target) throws IOException {
        if (!source.toFile().exists()) return false;
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        return true;
    }

    public static void deleteFile(Path file) throws IOException {
        if (file.toFile().exists()) {
            Files.delete(file);
        }
    }

    public static void copyDirectory(File sourceDir, File destDir) throws IOException {
        // 如果目标目录不存在，则创建目标目录
        if (!destDir.exists() && !destDir.mkdirs()) {
            throw new IOException("Failed to create directory: " + destDir.getAbsolutePath());
        }

        // 使用 NIO 的 Files.walkFileTree 递归遍历源目录及其子目录
        Path sourcePath = sourceDir.toPath();
        Path destPath = destDir.toPath();

        Files.walkFileTree(sourcePath, new SimpleFileVisitor<>() {
            // 处理每一个文件
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                // 计算目标文件路径
                Path targetFile = destPath.resolve(sourcePath.relativize(file));
                // 复制文件
                Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }

            // 处理每一个目录
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                // 创建目录
                Path targetDir = destPath.resolve(sourcePath.relativize(dir));
                Files.createDirectories(targetDir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
