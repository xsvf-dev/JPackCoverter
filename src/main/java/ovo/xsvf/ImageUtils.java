package ovo.xsvf;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ImageUtils {
    public static void splitImage(Path imagePath, Path outputDir, String prefix, int numSplits) throws IOException{
        File imageFile = imagePath.toFile();
        BufferedImage img = ImageIO.read(imageFile);
        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();
        int splitHeight = imgHeight / numSplits;

        // Split the image
        for (int i = 0; i < numSplits; i++) {
            BufferedImage splitImg = img.getSubimage(0, i * splitHeight, imgWidth, splitHeight);
            File outputFile = outputDir.resolve(String.format("%s_%02d.png", prefix, i)).toFile();
            ImageIO.write(splitImg, "PNG", outputFile);
        }
    }

    // ɫ����������
    public static void adjustHue(BufferedImage img, float hueShift) {
        // �˴�������ʹ�� HSL �� HSV ɫ�ʿռ����ɫ������
        // ��ʱʹ�ü򵥵���ɫ���������������Ը�����Ҫʵ�ָ��ӵ�ɫ���㷨
        // ��ʵ��ʹ���п��Կ��Ǹ��߼���ɫ���㷨

        float[] hsb = new float[3];
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                Color color = new Color(img.getRGB(x, y), true);
                Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
                hsb[0] = (hsb[0] + hueShift) % 1.0f; // ɫ������
                int rgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
                img.setRGB(x, y, rgb);
            }
        }
    }

    // ���ȵ�������
    public static void adjustBrightnessForGrayscale(BufferedImage img, float brightnessAdjust) {
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                Color color = new Color(img.getRGB(x, y), true);
                int r = Math.min(255, Math.max(0, (int) (color.getRed() + brightnessAdjust)));
                int g = Math.min(255, Math.max(0, (int) (color.getGreen() + brightnessAdjust)));
                int b = Math.min(255, Math.max(0, (int) (color.getBlue() + brightnessAdjust)));
                img.setRGB(x, y, new Color(r, g, b, color.getAlpha()).getRGB());
            }
        }
    }

    public static BufferedImage rotateImage(BufferedImage img, int angle) {
        BufferedImage rotatedImage = new BufferedImage(img.getHeight(), img.getWidth(), img.getType());
        Graphics2D g2d = rotatedImage.createGraphics();

        // Rotate the image around the center
        g2d.rotate(Math.toRadians(angle), img.getWidth() / 2.0, img.getHeight() / 2.0);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }

    public static void changeWhiteToYellow(BufferedImage image) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int pixel = image.getRGB(x, y);

                int alpha = (pixel >> 24) & 0xFF;
                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = pixel & 0xFF;

                // If the pixel is near white (RGB range 180 to 255), change it to yellow (255, 255, 0)
                if (alpha > 0 && red >= 180 && red <= 255 && green >= 180 && green <= 255 && blue >= 180 && blue <= 255) {
                    image.setRGB(x, y, new Color(255, 255, 0, alpha).getRGB());  // Yellow color with original alpha
                }
            }
        }
    }

    public static void swapAndMirror(BufferedImage image, Rectangle box1, Rectangle box2) {
        // ����ͼ���С������������
        int scale_factor = calculateScaleFactor(image.getWidth());

        // �������������С
        Rectangle scaledBox1 = scaleRectangle(box1, scale_factor);
        Rectangle scaledBox2 = scaleRectangle(box2, scale_factor);

        // ��֤������ľ�������
        validateRectangle(image, scaledBox1);
        validateRectangle(image, scaledBox2);

        // ��ȡ�����������ͼ��
        BufferedImage region1 = deepCopy(image.getSubimage(
                scaledBox1.x, scaledBox1.y, scaledBox1.width, scaledBox1.height));
        BufferedImage region2 = deepCopy(image.getSubimage(
                scaledBox2.x, scaledBox2.y, scaledBox2.width, scaledBox2.height));

        // ��������
        Graphics2D g2d = image.createGraphics();
        g2d.drawImage(region2, scaledBox1.x, scaledBox1.y, null);
        g2d.drawImage(region1, scaledBox2.x, scaledBox2.y, null);

        // ����ת����1������2
        region1 = mirrorImage(region1);
        region2 = mirrorImage(region2);

        // ��������ͼ��ճ����ԭλ��
        g2d.drawImage(region1, scaledBox1.x, scaledBox1.y, null);
        g2d.drawImage(region2, scaledBox2.x, scaledBox2.y, null);

        g2d.dispose();
    }

    private static int calculateScaleFactor(int width) {
        if (width == 256) return 1;
        if (width == 512) return 2;
        if (width == 1024) return 4;
        if (width == 2048) return 8;
        return 1; // Ĭ����������
    }

    private static Rectangle scaleRectangle(Rectangle rect, int scale_factor) {
        return new Rectangle(
                rect.x / scale_factor,
                rect.y / scale_factor,
                rect.width / scale_factor,
                rect.height / scale_factor
        );
    }

    private static void validateRectangle(BufferedImage image, Rectangle rect) {
        if (rect.x < 0 || rect.y < 0 ||
                rect.x + rect.width > image.getWidth() ||
                rect.y + rect.height > image.getHeight()) {
            throw new IllegalArgumentException(
                    String.format("Rectangle (%d,%d,%d,%d) is outside image bounds (%d,%d)",
                            rect.x, rect.y, rect.width, rect.height,
                            image.getWidth(), image.getHeight())
            );
        }
    }

    public static BufferedImage deepCopy(BufferedImage source) {
        BufferedImage copy = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = copy.createGraphics();
        g2d.drawImage(source, 0, 0, null);
        g2d.dispose();
        return copy;
    }

    public static BufferedImage mirrorImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                result.setRGB(width - 1 - x, height - 1 - y, rgb);
            }
        }

        return result;
    }

    // �ƶ�����ĸ�������
    public static void moveRegion(BufferedImage image, int startX, int startY, int endX, int endY, int shiftX, int shiftY) {
        // ��������ĸ���
        BufferedImage region = image.getSubimage(startX, startY, endX - startX, endY - startY);
        BufferedImage regionCopy = new BufferedImage(
                region.getWidth(), region.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = regionCopy.createGraphics();
        g2d.drawImage(region, 0, 0, null);
        g2d.dispose();

        // ���ԭʼ����ʹ��͸��ɫ��䣩
        Graphics2D imageG2d = image.createGraphics();
        imageG2d.setComposite(AlphaComposite.Clear);
        imageG2d.fillRect(startX, startY, endX - startX, endY - startY);
        imageG2d.dispose();

        // ����λ�û�������
        Graphics2D finalG2d = image.createGraphics();
        finalG2d.drawImage(regionCopy, startX + shiftX, startY + shiftY, null);
        finalG2d.dispose();
    }

    // ��ɫ�������ĸ�������
    public static void colorFillRegion(BufferedImage image, int startX, int startY, int endX, int endY, int fillColor) {
        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                try {
                    image.setRGB(x, y, fillColor);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Error setting pixel at (" + x + "," + y + "): " + e.getMessage());
                }
            }
        }
    }

    // ���ƺ�ճ������ĸ�������
    public static void copyAndPasteRegion(BufferedImage image, int srcX1, int srcY1, int srcX2, int srcY2, int dstX, int dstY) {
        // ��������ĸ���
        BufferedImage region = image.getSubimage(srcX1, srcY1, srcX2 - srcX1, srcY2 - srcY1);
        BufferedImage regionCopy = new BufferedImage(
                region.getWidth(), region.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = regionCopy.createGraphics();
        g2d.drawImage(region, 0, 0, null);
        g2d.dispose();

        // ����λ�û�������
        Graphics2D imageG2d = image.createGraphics();
        imageG2d.drawImage(regionCopy, dstX, dstY, null);
        imageG2d.dispose();
    }

    // Convert RGB to HSV
    public static float[] rgbToHsv(int r, int g, int b) {
        float[] hsv = new float[3];
        Color.RGBtoHSB(r, g, b, hsv);
        return hsv;
    }

    // Convert HSV to RGB
    public static Color hsvToRgb(float h, float s, float v) {
        int rgb = Color.HSBtoRGB(h, s, v);
        return new Color(rgb);
    }
}
