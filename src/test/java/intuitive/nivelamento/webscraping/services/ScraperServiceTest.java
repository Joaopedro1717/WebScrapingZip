package intuitive.nivelamento.webscraping.services;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScraperServiceTest {

    @Test
    void testExtractPdfLinks() throws IOException {
        ScraperService scraperService = new ScraperService();
        List<String> pdfLinks = scraperService.extractPdfLinks();

        assertNotNull(pdfLinks, "A lista de links não pode ser nula");
        assertFalse(pdfLinks.isEmpty(), "A lista de links não pode estar vazia");
        assertTrue(pdfLinks.stream().allMatch(link -> link.endsWith(".pdf")),
                "Todos os links devem terminar com .pdf");
    }
}
