package webscraping;

import scraping.CompressorService;
import scraping.DownloaderService;
import scraping.ScraperService;
import java.util.List;
import java.util.stream.Collectors;

public class WebScrapingApplication {
    private static final String ZIP_FILE_PATH = "C:\\Users\\Public\\Downloads\\anexos.zip";

    public static void main(String[] args) {
        try {
            // 1. Obtém os links apenas dos anexos desejados
            List<String> fileUrls = ScraperService.extractPdfLinks().stream()
                    .filter(url -> url.contains("Anexo_I") || url.contains("Anexo_II")) // Filtra apenas os anexos corretos
                    .collect(Collectors.toList());

            if (fileUrls.isEmpty()) {
                System.out.println("Os anexos não foram encontrados. Encerrando...");
                return;
            }

            // 2. Baixa os arquivos em paralelo
            List<String> downloadedFiles = DownloaderService.downloadFilesParallel(fileUrls);

            // 3. Compacta os arquivos baixados
            CompressorService.zipFiles(downloadedFiles, ZIP_FILE_PATH);

            System.out.println("Processo concluído! Arquivo ZIP gerado em: " + ZIP_FILE_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
