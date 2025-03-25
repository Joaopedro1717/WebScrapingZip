package webscraping;

import scraping.CompressorService;
import scraping.DownloaderService;
import scraping.ScraperService;

public class WebScrapingApplication {
    private static final String ZIP_FILE_PATH = "C:\\Users\\Public\\Downloads\\anexos.zip";

    public static void main(String[] args) {
        try {
            // 1. Obtém os links dos anexos
            String[] fileUrls = ScraperService.extractPdfLinks().toArray(new String[0]); // Alterado aqui

            // 2. Baixa os arquivos
            String file1 = DownloaderService.downloadFile(fileUrls[0]);
            String file2 = DownloaderService.downloadFile(fileUrls[1]);

            // 3. Compacta os arquivos baixados
            CompressorService.zipFiles(new String[]{file1, file2}, ZIP_FILE_PATH);

            System.out.println("Processo concluído! Arquivo ZIP gerado em: " + ZIP_FILE_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
