package scraping;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.*;

public class DownloaderService {

    private static final String DOWNLOAD_DIR = "downloads/";

    public static String downloadFile(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        String fileName = Paths.get(new URL(fileUrl).getPath()).getFileName().toString();
        Path outputPath = Paths.get(DOWNLOAD_DIR, fileName);
        Files.createDirectories(outputPath.getParent());

        try (InputStream in = connection.getInputStream();
             OutputStream out = Files.newOutputStream(outputPath)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
        System.out.println("Download concluído: " + outputPath);
        return outputPath.toString();
    }
}
