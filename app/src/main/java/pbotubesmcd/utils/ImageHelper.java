package pbotubesmcd.utils;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class ImageHelper {
    public static ImageIcon getResizedIcon(String imagePath, int width, int height) {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            return null;
        }

        try {
            if (!imagePath.startsWith("/")) {
                imagePath = "/" + imagePath;
            }

            URL imgUrl = ImageHelper.class.getResource(imagePath);

            if (imgUrl != null) {
                ImageIcon icon = new ImageIcon(imgUrl);
                Image img = icon.getImage();

                Image resizedImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(resizedImage);
            }
            {
                System.err.println("Gambar tidak ditemukan: " + imagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
