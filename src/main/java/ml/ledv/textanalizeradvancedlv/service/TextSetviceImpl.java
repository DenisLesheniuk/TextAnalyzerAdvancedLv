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
 * Implementation of {@link TextService} interface.
 * Service for work with Text and Analyze  components
 *
 * @author Denis Lesheniuk
 * @version 1.0
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

    //Method return field file from Text object
    public File getFile() {
        return text.getFile();
    }

    //Method set field file from Text object
    public void setFile(File file) {
        text.setFile(file);
    }

    //Method return true if field file in the Text object exist
    public boolean isFileExist(File file) {
        return file.exists();
    }

    //Method to delete file
    public void deleteFile(){
        if(text.getFile().exists())
            text.getFile().delete();

    }
    //Method return Text object
    public Text getText(){
        return text;
    }

    /**
     * Method does text analysis to get full analizing(top10 & bracketCheck)
     * @return Analyze object with a result in field top10 = result &
     *                                         field bracketChek = corrent/Incorrect.
     */
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

    /**
     * Method does text analysis to get the 10 most frequently used words in the text.
     * Conjunctions, prepositions and pronouns are excluded from the statistics.
     * @return Analyze object with a result in field top10 = result
     *                                         field bracketChek = null.
     */
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

    /**
     * Method does text analysis to get the iformation about the correctness of the brackets in the text .

     * @return Analyze object with a result in field bracketChek = currect/incorrect.
     *                                         field top10 = null.
     */
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
