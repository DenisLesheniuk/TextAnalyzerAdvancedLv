package ml.ledv.textanalizeradvancedlv.model;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/* Analyze model
 *
 * @author Denis Lesheniuk
 * @version 1.0      *
 * **/
@Component
public class Analyze {
    private List<Map.Entry<String, Integer>> top10;
    private String bracketCheck;

    public List<Map.Entry<String, Integer>> getTop10() {
        return top10;
    }

    public void setTop10(List<Map.Entry<String, Integer>> top10) {
        this.top10 = top10;
    }

    public String getBracketCheck() {
        return bracketCheck;
    }

    public void setBracketCheck(String bracketCheck) {
        this.bracketCheck = bracketCheck;
    }
}
