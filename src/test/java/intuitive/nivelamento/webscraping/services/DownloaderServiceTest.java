package intuitive.nivelamento.webscraping.services;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class DownloaderServiceTest {

    private final DownloaderService downloaderService = new DownloaderService();

    @Test
    void testDownloadPdfLinks() throws ExecutionException, InterruptedException {
        List<String> urls = List.of("http://invalid-url");

        Exception exception = assertThrows(Exception.class, () -> {
            downloaderService.downloadFilesParallel(urls);
        });

        assertNotNull(exception);
    }

    @Test
    void testDownloadValidPdf() throws Exception {
        List<String> fileUrls = Collections.singletonList("https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf");
        List<String> downloadedFiles = downloaderService.downloadFilesParallel(fileUrls);

        assertFalse(downloadedFiles.isEmpty(), "Nenhum arquivo foi baixado!");
        assertTrue(downloadedFiles.get(0).endsWith(".pdf"), "O arquivo baixado não tem extensão .pdf");
        assertTrue(Files.exists(Paths.get(downloadedFiles.get(0))), "O arquivo baixado não existe no sistema de arquivos!");
    }

    @Test
    void testDownloadMultipleFiles() throws Exception {
        List<String> fileUrls = List.of(
                "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf",
                "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"
        );

        List<String> downloadedFiles = downloaderService.downloadFilesParallel(fileUrls);

        assertEquals(2, downloadedFiles.size(), "Nem todos os arquivos foram baixados!");
        for (String filePath : downloadedFiles) {
            assertTrue(Files.exists(Paths.get(filePath)), "Arquivo não foi encontrado no sistema!");
        }
    }

    @Test
    void testDownloadInvalidUrl() {
        List<String> fileUrls = Collections.singletonList("https://example.com/arquivo_inexistente.pdf");

        Exception exception = assertThrows(Exception.class, () -> {
            downloaderService.downloadFilesParallel(fileUrls);
        });

        assertNotNull(exception, "Uma excessão deve ser lançada para URL inválida!");
    }

    @Test
    void testDownloadNonPdfFile() throws Exception {
        List<String> fileUrls = Collections.singletonList("https://www.w3.org/TR/PNG/iso_8859-1.txt");

        List<String> downloadedFiles = downloaderService.downloadFilesParallel(fileUrls);

        assertFalse(downloadedFiles.isEmpty(), "Nenhum arquivo foi baixado!");
        assertTrue(downloadedFiles.get(0).endsWith(".txt"), "O arquivo baixado deveria ser um .txt!");
    }

    @Test
    void testDownloadDirectoryExists() throws Exception {
        Path downloadDir = Paths.get("downloads/");
        Files.createDirectories(downloadDir);  // Criando o diretório manualmente

        downloaderService.downloadFilesParallel(Collections.singletonList("https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"));

        assertTrue(Files.exists(downloadDir), "O diretório de download deveria existir!");
    }

}
