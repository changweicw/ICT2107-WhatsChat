package sample.HelperFunctions;

import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ImageStream {

    /**
     *
     * This function allows you to grab images/gifs from online/external sources
     *
     *
     */

    public Image createImage(String url) throws IOException {
        // You have to set an User-Agent in case you get HTTP Error 403
        // respond while you trying to get the Image from URL.
        URLConnection conn = new URL(url).openConnection();
        conn.setRequestProperty("User-Agent", "Wget/1.13.4 (linux-gnu)");
        try (InputStream stream = conn.getInputStream()) {
            return new Image(stream);
        }
    }
}
