package intuitive.nivelamento.webscraping.services;
import org.junit.jupiter.api.*;
import java.nio.file.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompressorServiceTest {

    private final CompressorService compressorService = new CompressorService();
    private static final String ZIP_FILE_PATH = "test-output/anexos.zip";
    private static final String TEST_FILE_1 = "test-output/file1.pdf";
    private static final String TEST_FILE_2 = "test-output/file2.pdf";

    @BeforeEach
    void setUp() throws Exception {
        Files.createDirectories(Paths.get("test-output"));
        Files.write(Paths.get(TEST_FILE_1), "Test PDF Content 1".getBytes());
        Files.write(Paths.get(TEST_FILE_2), "Test PDF Content 2".getBytes());
    }

    @Test
    void testZipFilesSuccess() throws Exception {
        List<String> filesToZip = List.of(TEST_FILE_1, TEST_FILE_2);
        compressorService.zipFiles(filesToZip, ZIP_FILE_PATH);

        assertTrue(Files.exists(Paths.get(ZIP_FILE_PATH)), "O arquivo ZIP não foi criado!");
    }

    @Test
    void testZipNonExistentFile() {
        List<String> filesToZip = List.of("test-output/non_existent.pdf");

        Exception exception = assertThrows(Exception.class, () -> {
            compressorService.zipFiles(filesToZip, ZIP_FILE_PATH);
        });

        assertNotNull(exception, "Uma exceção deveria ser lançada para arquivos inexistentes!");
    }

    @AfterEach
    void tearDown() throws Exception {
        Files.deleteIfExists(Paths.get(TEST_FILE_1));
        Files.deleteIfExists(Paths.get(TEST_FILE_2));
        Files.deleteIfExists(Paths.get(ZIP_FILE_PATH));
    }
}
