package ml.ledv.textanalizeradvancedlv.service;

import java.io.File;

/**
 * Simple File Handler
 *
 * @author Denis Lesheniuk
 * @version 1.1
 **/
public interface FileHandler {
    public String textExtractor(String filePath);
    public String textExtractor(File file);

};
