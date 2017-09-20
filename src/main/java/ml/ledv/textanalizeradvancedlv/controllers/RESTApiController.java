package ml.ledv.textanalizeradvancedlv.controllers;

import ml.ledv.textanalizeradvancedlv.model.Analyze;
import ml.ledv.textanalizeradvancedlv.model.Text;
import ml.ledv.textanalizeradvancedlv.service.TextService;
import ml.ledv.textanalizeradvancedlv.utils.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@RestController
@RequestMapping("/api")
public class RESTApiController {
    private static Logger logger = LoggerFactory.getLogger(RESTApiController.class);

    @Autowired
    private TextService textService;

    @RequestMapping(value = "/textFile", method = RequestMethod.POST)
    public ResponseEntity<?> handleFileUpload(@RequestParam("file")MultipartFile file){
        File upfile = new File("uploaded.txt");
        if(file.isEmpty()){
            logger.error("File is empty");
            return new ResponseEntity(new CustomErrorType("File is empty"), HttpStatus.CONFLICT);
        }
        if(!file.getOriginalFilename().endsWith(".txt")){
            logger.error("File format error");
            return new ResponseEntity(new CustomErrorType("File format error"), HttpStatus.CONFLICT);
        }

        try {

            logger.info(file.getOriginalFilename());
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(upfile));
            stream.write(bytes);
            stream.close();
            textService.setFile(upfile);
        } catch (Exception e) {
            e.getMessage();
        }


        return new ResponseEntity<Text>(textService.getText() , HttpStatus.CREATED);
    }

    @RequestMapping(value = "/textFile", method = RequestMethod.DELETE)
        public ResponseEntity<Text> deleteText(){
            logger.info("Deleting file");
            textService.deleteFile();
        return new ResponseEntity<Text>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "textFile/analyze", method = RequestMethod.GET)
        public ResponseEntity<Analyze> analyze(){
         File upfile = new File("uploaded.txt");
            if(!upfile.exists()){
            logger.error("File is empty");
            return new ResponseEntity(new CustomErrorType("The file is not exist, please add the file."), HttpStatus.CONFLICT);
        }
            logger.info("analyzing");
           Analyze analyze = textService.fullTextanalize();
        return new ResponseEntity<Analyze>(analyze, HttpStatus.OK);
    }
}
