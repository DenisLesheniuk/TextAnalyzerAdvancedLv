package ml.ledv.textanalizeradvancedlv.controllers;

import ml.ledv.textanalizeradvancedlv.service.FileHandler;
import ml.ledv.textanalizeradvancedlv.service.TextAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class AnalyzeController {

    @Autowired
    FileHandler fileHandlerImpForResouces;
    @Autowired
    TextAnalyzer textAnalizer;

    @GetMapping("/analyzing")
    public String analyzing(RedirectAttributes redirectAttributes){
        String text = fileHandlerImpForResouces.textExtractor("uploaded/uploaded.txt");

        List<Map.Entry<String, Integer>> sortedReiting = textAnalizer.topTenRepeatingWords(text);
        int count = 0;
        List <String> result = new ArrayList<>();
        for(Map.Entry<String, Integer> word: sortedReiting){
            if(count == 10 && !word.getValue().equals(""))break;
            result.add("\t" + (count+1) + ":" + "\t" +  word.getKey());
            count++;
        }

        redirectAttributes.addFlashAttribute("result", result);

        String bracketResult = textAnalizer.bracketChecker(text);

        redirectAttributes.addFlashAttribute("bracketResult", bracketResult);

        return "redirect:analyze";
    }

    @GetMapping("/analyzing/top10")
    public String top10(RedirectAttributes redirectAttributes){
        String text = fileHandlerImpForResouces.textExtractor("uploaded/uploaded.txt");

        List<Map.Entry<String, Integer>> sortedReiting = textAnalizer.topTenRepeatingWords(text);
        int count = 0;
        List <String> result = new ArrayList<>();
        for(Map.Entry<String, Integer> word: sortedReiting){
            if(count == 10 && !word.getValue().equals(""))break;
            result.add("\t" + (count+1) + ":" + "\t" +  word.getKey());
            count++;
        }

        redirectAttributes.addFlashAttribute("result", result);
        return "redirect:/analyze";
    }

    @GetMapping("/analyzing/bracketCheck")
    public String bracketCheck(RedirectAttributes redirectAttributes){
        String text = fileHandlerImpForResouces.textExtractor("uploaded/uploaded.txt");
        String bracketResult = textAnalizer.bracketChecker(text);

        redirectAttributes.addFlashAttribute("bracketResult", bracketResult);

        return "redirect:/analyze";
    }
}
