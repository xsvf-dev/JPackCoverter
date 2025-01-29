package ovo.xsvf.transformers;

import ovo.xsvf.FileUtils;
import ovo.xsvf.ImageUtils;
import ovo.xsvf.api.ITransformer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;

public class ArmorLayersTransformer implements ITransformer {
    @Override
    public void transform(File inDir, File outDir, HashMap<String, Boolean> processed) throws IOException {
        // os.path.join(assets_path, 'models', 'armor')
        Path modelsArmorPath = Path.of("assets", "minecraft", "textures", "models", "armor");
        Path path = inDir.toPath().resolve(modelsArmorPath);
        if (!path.toFile().exists()) {
            System.out.println("Armor layers not found.");
            return;
        }

        for (String armorFile : List.of("diamond_layer_1.png", "diamond_layer_2.png")) {

            Path originalPath = path.resolve(armorFile);
            Path newPath = outDir.toPath().resolve(modelsArmorPath).resolve(armorFile.replace("diamond", "netherite"));

            if (Files.exists(originalPath)) {
                BufferedImage img = ImageIO.read(originalPath.toFile());
                int width = img.getWidth();
                int height = img.getHeight();
                BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                WritableRaster raster = newImage.getRaster();

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int argb = img.getRGB(x, y);
                        int alpha = (argb >> 24) & 0xff;
                        if (alpha == 0) {
                            raster.setDataElements(x, y, new int[] { argb });
                        } else {
                            Color color = new Color(argb, true);
                            float[] hsva = ImageUtils.rgbToHsv(color.getRed(), color.getGreen(), color.getBlue());
                            float newHue = 310 / 360.0f;
                            float newSaturation = hsva[1] / 3;
                            float newValue = hsva[2] / 3;
                            Color newColor = ImageUtils.hsvToRgb(newHue, newSaturation, newValue);
                            raster.setDataElements(x, y, new int[] { newColor.getRGB() });
                        }
                    }
                }

                FileUtils.checkAndCreateParentDir(newPath.toFile());
                ImageIO.write(newImage, "PNG", newPath.toFile());
            }
        }
    }
}
