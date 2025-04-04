package intuitive.nivelamento.webscraping.services;

import intuitive.nivelamento.webscraping.interfaces.CompressorInterface;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CompressorService implements CompressorInterface {

    public void zipFiles(List<String> files, String zipFilePath) throws IOException {
        try (ZipOutputStream zipOut = new ZipOutputStream(Files.newOutputStream(Paths.get(zipFilePath)))) {
            for (String filePath : files) {
                File fileToZip = new File(filePath);
                try (FileInputStream fis = new FileInputStream(fileToZip)) {
                    ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                    zipOut.putNextEntry(zipEntry);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) >= 0) {
                        zipOut.write(buffer, 0, length);
                    }
                    System.out.println("Arquivo adicionado ao ZIP: " + fileToZip.getName());
                }
            }
        }
    }
}
