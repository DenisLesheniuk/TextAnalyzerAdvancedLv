package ml.ledv.textanalizeradvancedlv.service;

import ml.ledv.textanalizeradvancedlv.model.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TextSetviceImpl implements TextService {

    @Autowired
    private Text text;

    public File getFile() {
        return text.getFile();
    }

    public void setFile(File file) {
        text.setFile(file);
    }

    @Override
    public boolean isFileExist(File file) {
        return file.exists();
    }

    public void deleteFile(){
        if(text.getFile().exists())
            text.getFile().delete();
    }

    public Text getText(){
        return text;
    }
}
