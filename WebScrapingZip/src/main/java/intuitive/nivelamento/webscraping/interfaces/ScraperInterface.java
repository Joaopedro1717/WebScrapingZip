package intuitive.nivelamento.webscraping.interfaces;

import java.io.IOException;
import java.util.List;

public interface ScraperInterface {
    List<String> extractPdfLinks() throws IOException;
}
