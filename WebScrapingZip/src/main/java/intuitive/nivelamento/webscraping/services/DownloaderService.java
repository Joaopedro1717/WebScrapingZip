package intuitive.nivelamento.webscraping.services;

import intuitive.nivelamento.webscraping.interfaces.DownloaderInterface;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class DownloaderService implements DownloaderInterface {

    private static final String DOWNLOAD_DIR = "downloads/";

    @Override
    public List<String> downloadFilesParallel(List<String> fileUrls) throws InterruptedException, ExecutionException, IOException {
        Files.createDirectories(Paths.get(DOWNLOAD_DIR));

        ExecutorService executor = Executors.newFixedThreadPool(2);
        List<Future<String>> futures = fileUrls.stream()
                .map(url -> executor.submit(() -> downloadFile(url)))
                .collect(Collectors.toList());

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);

        return futures.stream().map(DownloaderService::getFutureResult).collect(Collectors.toList());
    }

    private static String getFutureResult(Future<String> future) {
        try {
            return future.get();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar download", e);
        }
    }

    private static String downloadFile(String fileUrl) throws IOException {
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
