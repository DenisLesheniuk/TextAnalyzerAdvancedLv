package ml.ledv.textanalizeradvancedlv.service;

import ml.ledv.textanalizeradvancedlv.model.Analyze;
import ml.ledv.textanalizeradvancedlv.model.Text;
import ml.ledv.textanalizeradvancedlv.service.analizator.FileHandler;
import ml.ledv.textanalizeradvancedlv.service.analizator.TextAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service for work with Text and Analyze  components
 *
 * **/
@Service("textService")
public class TextSetviceImpl implements TextService {

    @Autowired
    private TextAnalyzer textAnalyzer; // service for analizing text.
    @Autowired
    private FileHandler fileHandlerImpl;// service for file handing.

    @Autowired
    private Text text;
    @Autowired
    private Analyze analyze;

    public File getFile() {
        return text.getFile();
    }

    public void setFile(File file) {
        text.setFile(file);
    }

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

    public Analyze fullTextAnalyze(){
        String txt = "";
        txt = fileHandlerImpl.textExtractor(text.getFile());
        List<Map.Entry<String, Integer>> sortedReiting = textAnalyzer.topTenRepeatingWords(txt);
        List<Map.Entry<String, Integer>> top10 = new ArrayList<>();
        int count = 0;
        List <String> result = new ArrayList<>();
        for(Map.Entry<String, Integer> word: sortedReiting){
            if(count == 10 && !word.getValue().equals(""))break;
            top10.add(word);
            count++;
        }

        String bracketResult = textAnalyzer.bracketChecker(txt);
        analyze.setTop10(top10);
        analyze.setBracketCheck(bracketResult);

        return analyze;
    }

    public Analyze top10TextAnalyze() {
        String txt = "";
        txt = fileHandlerImpl.textExtractor(text.getFile());
        List<Map.Entry<String, Integer>> sortedReiting = textAnalyzer.topTenRepeatingWords(txt);
        List<Map.Entry<String, Integer>> top10 = new ArrayList<>();
        int count = 0;
        List <String> result = new ArrayList<>();
        for(Map.Entry<String, Integer> word: sortedReiting){
            if(count == 10 && !word.getValue().equals(""))break;
            top10.add(word);
            count++;
        }
        analyze.setTop10(top10);
        analyze.setBracketCheck(null);
        return analyze;
    }

    @Override
    public Analyze bracketCheckTextAnalyze() {
        String txt = "";
        txt = fileHandlerImpl.textExtractor(text.getFile());

        String bracketResult = textAnalyzer.bracketChecker(txt);
        analyze.setTop10(null);
        analyze.setBracketCheck(bracketResult);
        return analyze;
    }
}
