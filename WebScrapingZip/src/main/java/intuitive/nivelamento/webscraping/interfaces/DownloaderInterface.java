package intuitive.nivelamento.webscraping.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface DownloaderInterface {
    List<String> downloadFilesParallel(List<String> fileUrls) throws InterruptedException, ExecutionException, IOException;
}
