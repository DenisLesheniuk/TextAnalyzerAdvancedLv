package ml.ledv.textanalizeradvancedlv.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Implementation of {@link FileHandler} interface.
 * This class provides methods for file processing.
 * Intended to extract text from files in the resources folder.
 *
 * @author Denis Lesheniuk
 * @version 1.1
 **/
@Service("fileHandlerImpForResouces")
public class FileHandlerImplForResources implements FileHandler {

    private boolean fileOk = false;
    /**
     * Extracts text from a file.
     * @param filePath is a relative path.
     * @return text in String format

     */
    public String textExtractor(String filePath) {
        String text = "";
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/" + filePath), "UTF-8"))) {
                while (bufferedReader.ready()) {
                    text += bufferedReader.readLine() + "\n";
                }

            } catch (IOException exc) {
                exc.printStackTrace();
            }
        return text;
    }

    /**
     * File validation
     * @param filePath is a relative path.
     * @return true if the file is validated, else @return false

     */
    public boolean fileValidation(String filePath){
        return fileOk;
    }


}
