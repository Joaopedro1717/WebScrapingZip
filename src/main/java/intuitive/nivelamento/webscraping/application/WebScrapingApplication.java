package intuitive.nivelamento.webscraping.application;

import intuitive.nivelamento.webscraping.interfaces.CompressorInterface;
import intuitive.nivelamento.webscraping.interfaces.DownloaderInterface;
import intuitive.nivelamento.webscraping.interfaces.ScraperInterface;

import java.util.List;
import java.util.stream.Collectors;

public class WebScrapingApplication {
    private static final String ZIP_FILE_PATH = "C:\\Users\\Public\\Downloads\\anexos.zip";

    private final ScraperInterface scraper;
    private final DownloaderInterface downloader;
    private final CompressorInterface compressor;
    
    public WebScrapingApplication(ScraperInterface scraper, DownloaderInterface downloader, CompressorInterface compressor) {
        this.scraper = scraper;
        this.downloader = downloader;
        this.compressor = compressor;
    }

    public void execute() {
        try {
            List<String> fileUrls = scraper.extractPdfLinks().stream()
                    .filter(url -> url.contains("Anexo_I") || url.contains("Anexo_II"))
                    .collect(Collectors.toList());

            if (fileUrls.isEmpty()) {
                System.out.println("Os anexos não foram encontrados. Encerrando...");
                return;
            }

            List<String> downloadedFiles = downloader.downloadFilesParallel(fileUrls);
            compressor.zipFiles(downloadedFiles, ZIP_FILE_PATH);

            System.out.println("Processo concluído! Arquivo ZIP gerado em: " + ZIP_FILE_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WebScrapingApplication app = new WebScrapingApplication(
                new intuitive.nivelamento.webscraping.services.ScraperService(),
                new intuitive.nivelamento.webscraping.services.DownloaderService(),
                new intuitive.nivelamento.webscraping.services.CompressorService()
        );

        app.execute();
    }
}
