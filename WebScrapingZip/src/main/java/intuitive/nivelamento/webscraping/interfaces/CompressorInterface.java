package intuitive.nivelamento.webscraping.interfaces;

import java.io.IOException;
import java.util.List;

public interface CompressorInterface {
    void zipFiles(List<String> files, String zipFilePath) throws IOException;
}
