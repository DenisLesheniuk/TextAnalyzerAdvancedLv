package ml.ledv.textanalizeradvancedlv.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Controller
public class FileUploadController {
    private static Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
        public String provideUploadInfo(){
            return "upload";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes){
            if(file.isEmpty()){
                redirectAttributes.addFlashAttribute("message", "File is empty. Please select a file to upload");
                return "redirect:upload";
            }
        if(!file.getOriginalFilename().endsWith(".txt")){
            redirectAttributes.addFlashAttribute("message", "Incorrect file format. Please select a file to upload");
            return "redirect:upload";
        }
                try{

                    logger.info(file.getOriginalFilename());
                    byte[] bytes = file.getBytes();
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/uploaded/uploaded.txt")));
                    stream.write(bytes);
                    stream.close();
                   redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename());
                }catch (Exception e){
                    e.getMessage();
                }

                return "redirect:/analyze";

    }

    @GetMapping("/analyze")
    public String uploadStatus(){
        return "analyze";
    }

}
