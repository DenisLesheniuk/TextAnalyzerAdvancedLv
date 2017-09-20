package ml.ledv.textanalizeradvancedlv.service.analizator;

import ml.ledv.textanalizeradvancedlv.service.analizator.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class provides methods for receiving stop words in the format String.
 *
 * @author Denis Lesheniuk
 * @version 1.0
 * **/
@Service
public class StopWords {

    @Autowired
    private FileHandler fileHandlerImpForResouces;

    public String getAllStopWords(){
        String stopWords = fileHandlerImpForResouces.textExtractor("words/conjunctions.txt") + fileHandlerImpForResouces.textExtractor("words/prepositions.txt") + fileHandlerImpForResouces.textExtractor("words/pronouns.txt");
        return stopWords;
    }
}
