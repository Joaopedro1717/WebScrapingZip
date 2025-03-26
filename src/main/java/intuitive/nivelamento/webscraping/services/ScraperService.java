package intuitive.nivelamento.webscraping.services;

import intuitive.nivelamento.webscraping.interfaces.ScraperInterface;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScraperService implements ScraperInterface {

    private static final String SITE_URL = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos";

    @Override
    public List<String> extractPdfLinks() throws IOException {
        Document doc = Jsoup.connect(SITE_URL)
                .timeout(10_000)
                .get();

        Elements links = doc.select("a.internal-link[href]");
        List<String> pdfLinks = new ArrayList<>();

        if (links.isEmpty()) {
            throw new IllegalStateException("Nenhum link encontrado!");
        }

        for (Element link : links) {
            String url = link.absUrl("href");

            if (url.toLowerCase().endsWith(".pdf")) {
                pdfLinks.add(url);
            }
        }

        return pdfLinks;
    }
}
