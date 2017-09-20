package ml.ledv.textanalizeradvancedlv.service;

import ml.ledv.textanalizeradvancedlv.model.Analyze;
import ml.ledv.textanalizeradvancedlv.model.Text;

import java.io.File;
import java.util.List;

public interface TextService {
    public File getFile();
    public void setFile(File texts);
    public boolean isFileExist(File text);
    public void deleteFile();
    public Text getText();
    public Analyze fullTextanalize();
}
