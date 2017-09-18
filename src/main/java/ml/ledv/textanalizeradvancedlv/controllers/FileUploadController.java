package ml.ledv.textanalizeradvancedlv.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Controller
public class FileUploadController {
    private static Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
        public @ResponseBody String provideUploadInfo(){
            return "Вы можете загружать файл с использованием того же URL.";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("file")MultipartFile file){
            if(!file.isEmpty()){
                try{
                    if(!file.getOriginalFilename().endsWith(".txt"))return "Вам не удалось загрузить потому что данный формат файла не поддерживается";
                    logger.info(file.getOriginalFilename());
                    byte[] bytes = file.getBytes();
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/uploaded/uploaded.txt")));
                    stream.write(bytes);
                    stream.close();
                    return "Загружен файл: " + file.getOriginalFilename();
                }catch (Exception e){
                    return "Вам не удалось загрузить => " + e.getMessage();
                }
            }else{
                return "Вам не удалось загрузить потому что файл пустой.";
            }
    }

}
