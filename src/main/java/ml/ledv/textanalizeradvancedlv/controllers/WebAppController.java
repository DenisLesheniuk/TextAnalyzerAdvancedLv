package ml.ledv.textanalizeradvancedlv.controllers;

import ml.ledv.textanalizeradvancedlv.model.Analyze;
import ml.ledv.textanalizeradvancedlv.service.TextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class WebAppController {

    private static Logger logger = LoggerFactory.getLogger(WebAppController.class);
    @Autowired
    private TextService textService;

    @RequestMapping(value = "/textFile", method = RequestMethod.GET)
    public String provideUploadInfo(){
        return "textFile";
    }

    @RequestMapping(value = "/textFile", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes){
        File upfile = new File("uploaded.txt");
        if(upfile.exists())
            upfile.delete();
        if(file.isEmpty()){
            redirectAttributes.addFlashAttribute("message", "File is empty. Please select a file to upload");
            return "redirect:textFile";
        }
        if(!file.getOriginalFilename().endsWith(".txt")){
            redirectAttributes.addFlashAttribute("message", "Incorrect file format. Please select a file to upload");
            return "redirect:textFile";
        }

        try (BufferedOutputStream stream =
                     new BufferedOutputStream(new FileOutputStream(upfile))){
            logger.info(file.getOriginalFilename());
            byte[] bytes = file.getBytes();
            stream.write(bytes);
            textService.setFile(upfile);
            redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename());
        } catch (Exception e) {
            e.getMessage();
        }

        return "redirect:/analyze";
    }


    @GetMapping("/analyze")
    public String uploadStatus(){
        return "analyze";
    }

    @GetMapping("/analyzing")
    public String analyzing(RedirectAttributes redirectAttributes){
        Analyze analyze = textService.fullTextAnalyze();
        List<String> words = new ArrayList<>();
        List<Integer> raiting = new ArrayList<>();
        for(Map.Entry <String , Integer > map: analyze.getTop10()){
            words.add(map.getKey());
            raiting.add(map.getValue());
        }
        redirectAttributes.addFlashAttribute("raiting", raiting);
        redirectAttributes.addFlashAttribute("words", words);
        redirectAttributes.addFlashAttribute("bracketResult", analyze.getBracketCheck());
        return "redirect:analyze";
    }

    @GetMapping("/analyzing/top10")
    public String top10(RedirectAttributes redirectAttributes){
        Analyze analyze = textService.top10TextAnalyze();
        List<String> words = new ArrayList<>();
        List<Integer> raiting = new ArrayList<>();
        for(Map.Entry <String , Integer > map: analyze.getTop10()){
            words.add(map.getKey());
            raiting.add(map.getValue());
        }
        redirectAttributes.addFlashAttribute("raiting", raiting);
        redirectAttributes.addFlashAttribute("words", words);

        return "redirect:/analyze";
    }

    @GetMapping("/analyzing/bracketCheck")
    public String bracketCheck(RedirectAttributes redirectAttributes){
        Analyze analyze = textService.bracketCheckTextAnalyze();
        redirectAttributes.addFlashAttribute("bracketResult", analyze.getBracketCheck());
        return "redirect:/analyze";
    }

}

