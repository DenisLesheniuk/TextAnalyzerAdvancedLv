package ml.ledv.textanalizeradvancedlv.service;

import org.springframework.stereotype.Service;

import java.io.*;

/**
 * Implementation of {@link FileHandler} interface.
 * This class provides methods for file processing and file validation.
 * @author Denis Lesheniuk
 * @version 1.1
 **/
@Service("fileHandlerImpl")
public class FileHandlerImpl implements FileHandler {


    /**
     * Extracts text from a file.
     *
     * @param filePath is an absolute path
     * @return text in String format
     */
    public String textExtractor(String filePath) {
        StringBuilder textBuilder = new StringBuilder();

            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
                while (bufferedReader.ready()) {
                    textBuilder.append(bufferedReader.readLine()).append("\n");
                }
            } catch (IOException exc) {
                exc.printStackTrace();
            }

        return textBuilder.toString();
    }

    /**
     * Extracts text from a file.
     *
     * @param file is a File object
     * @return text in String format
     */

    public String textExtractor(File file) {
        StringBuilder textBuilder = new StringBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            while (bufferedReader.ready()) {
                textBuilder.append(bufferedReader.readLine()).append("\n");
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        return textBuilder.toString();
    }



}
