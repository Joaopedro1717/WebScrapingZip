package intuitive.nivelamento.webscraping.services;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScraperServiceTest {

    private final ScraperService scraperService = new ScraperService();

    @Test
    void testExtractPdfLinks() throws IOException {
        List<String> pdfLinks = scraperService.extractPdfLinks();

        assertNotNull(pdfLinks, "A lista de links não pode ser nula");
        assertFalse(pdfLinks.isEmpty(), "A lista de links não pode estar vazia");

        for (String link : pdfLinks) {
            assertTrue(link.endsWith(".pdf"), "O link extraído não é um PDF: " + link);
        }
    }

    @Test
    void testIgnoreNonPdfLinks() throws Exception {
        List<String> pdfLinks = scraperService.extractPdfLinks();

        boolean hasNonPdf = pdfLinks.stream().anyMatch(link -> !link.endsWith(".pdf"));
        assertFalse(hasNonPdf, "A lista contém links que não são PDFs!");
    }

}
