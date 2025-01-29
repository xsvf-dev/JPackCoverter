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
        // ���Ŀ��Ŀ¼�����ڣ��򴴽�Ŀ��Ŀ¼
        if (!destDir.exists() && !destDir.mkdirs()) {
            throw new IOException("Failed to create directory: " + destDir.getAbsolutePath());
        }

        // ʹ�� NIO �� Files.walkFileTree �ݹ����ԴĿ¼������Ŀ¼
        Path sourcePath = sourceDir.toPath();
        Path destPath = destDir.toPath();

        Files.walkFileTree(sourcePath, new SimpleFileVisitor<>() {
            // ����ÿһ���ļ�
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                // ����Ŀ���ļ�·��
                Path targetFile = destPath.resolve(sourcePath.relativize(file));
                // �����ļ�
                Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }

            // ����ÿһ��Ŀ¼
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                // ����Ŀ¼
                Path targetDir = destPath.resolve(sourcePath.relativize(dir));
                Files.createDirectories(targetDir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
