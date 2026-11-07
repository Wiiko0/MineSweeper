package view;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ImageAssets {
    private static final String RESOURCE_FOLDER = "/images/";
    private static final Path FILE_FOLDER = Path.of("resources", "images");

    private ImageAssets() {
    }

    public static ImageIcon loadIcon(String fileName, int width, int height) {
        try (InputStream resource = ImageAssets.class.getResourceAsStream(RESOURCE_FOLDER + fileName)) {
            if (resource != null) {
                return scale(new ImageIcon(resource.readAllBytes()), width, height);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Could not read image resource: " + fileName, exception);
        }

        Path file = FILE_FOLDER.resolve(fileName);
        if (Files.exists(file)) {
            return scale(new ImageIcon(file.toString()), width, height);
        }
        throw new IllegalStateException(
                "Missing image '" + fileName + "'. Add it to resources\\images.");
    }

    private static ImageIcon scale(ImageIcon icon, int width, int height) {
        Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}
