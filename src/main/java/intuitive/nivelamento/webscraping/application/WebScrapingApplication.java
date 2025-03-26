package intuitive.nivelamento.webscraping.application;

import intuitive.nivelamento.webscraping.services.CompressorService;
import intuitive.nivelamento.webscraping.services.DownloaderService;
import intuitive.nivelamento.webscraping.services.ScraperService;
import java.util.List;
import java.util.stream.Collectors;

public class WebScrapingApplication {
    private static final String ZIP_FILE_PATH = "C:\\Users\\Public\\Downloads\\anexos.zip";

    public static void main(String[] args) {
        try {

            List<String> fileUrls = ScraperService.extractPdfLinks().stream()
                    .filter(url -> url.contains("Anexo_I") || url.contains("Anexo_II"))
                    .collect(Collectors.toList());

            if (fileUrls.isEmpty()) {
                System.out.println("Os anexos não foram encontrados. Encerrando...");
                return;
            }

            List<String> downloadedFiles = DownloaderService.downloadFilesParallel(fileUrls);

            CompressorService.zipFiles(downloadedFiles, ZIP_FILE_PATH);

            System.out.println("Processo concluído! Arquivo ZIP gerado em: " + ZIP_FILE_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
