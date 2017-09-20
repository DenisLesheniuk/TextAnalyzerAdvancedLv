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
import java.util.List;

/**
 *                              Rest based controller, implementing REST API.
 *
 * To work with api first you need to upload the file on the server
 * POST request to /api/textFile/ with a Content-Type: multipart/form-data loads the specified file
 *
 * *********************************************************************************************************
 *     Example of the generated query:
 *          POST /api/textFile/ HTTP/1.1
 *          Host: localhost:8080
 *          Cache-Control: no-cache
 *          Postman-Token: af4ba0d3-de70-b4d0-b905-f4592cc1773c
 *          Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW
 *
 *          ------WebKitFormBoundary7MA4YWxkTrZu0gW
 *          Content-Disposition: form-data; name="file"; filename="text2.txt"
 *          Content-Type: text/plain
 *          ------WebKitFormBoundary7MA4YWxkTrZu0gW--
 * *********************************************************************************************************
 *
 *  If the file format is incorrect, you will get Http Status 409 - Conflict, and response body of the JSON form:
 *
 *      {
 *         "errorMessage": "File format error"
 *      }
 *
 *  If the file is empty, you will get Http Status 409 - Conflict, and response body of the JSON form:
 *
 *      {
 *          "errorMessage": "File is empty"
 *      }
 *
 *  If the file is validated, get you will get Http Status 201 - Created.
 *
 *  Ok... The file is uploaded, now you can use the following methods:
 *
 *  DELETE request to /api/textFile/ deletes the file from the server.
 *
 * *********************************************************************************************************
 *     Example of the generated query:
 *           DELETE /api/textFile/ HTTP/1.1
 *           Host: localhost:8080
 *           Cache-Control: no-cache
 *           Postman-Token: e5b2d94d-b9f5-7fb4-5de6-4cb313afcc26
 *           Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW
 * *********************************************************************************************************
 *  If the file is not exist, you will get Http Status 409 - Conflict, and response body of the JSON form:
 *
 *      {
 *           "errorMessage": "The file is not exist."
 *      }
 *
 *  If the file is validated, you will get Http Status 204 - No Content.
 *
 * GET request to /api/textFile/analyze/ Counting the number of repeated words in the text,
 * and verify that the brackets in the text are correctly positioned.
 * Conjunctions, prepositions and pronouns are excluded from the statistics.
 * It returns a top 10 list, and status bracketCheck:
 *
 * *********************************************************************************************************
 *      Example of the generated query:
 *           GET /api/textFile/analyze HTTP/1.1
 *           Host: localhost:8080
 *           Cache-Control: no-cache
 *           Postman-Token: c1622e87-44a2-93e1-46bf-90265b0055c6
 * *********************************************************************************************************
 *  If the file is not exist, you will get Http Status 409 - Conflict, and response body of the JSON form:
 *
 *      {
 *           "errorMessage": "The file is not exist."
 *      }
 *
 *  If the file is validated, you will get Http Status 200 - OK, and response body of the JSON form:
 *
 * {
 *       "top10": [
 *            {
 *           "книге": 6
 *           },
 *           {
 *           "людей": 5
 *           },
 *           {
 *           "прошу": 4
 *           },
 *           {
 *           "человек": 3
 *           },
 *           {
 *           "сословия": 3
 *           },
 *           {
 *           "уже": 3
 *           },
 *           {
 *           "жизнь": 3
 *           },
 *           {
 *           "будет": 3
 *           },
 *           {
 *           "замечания": 3
 *           },
 *           {
 *           "высшего": 2
 *           }
 *           ],
 *           "bracketCheck": "correct"
 *  }
 *
 * GET request to /api/textFile/analyze/top10 Counting the number of repeated words in the text.
 * Conjunctions, prepositions and pronouns are excluded from the statistics.
 * It returns only top 10 list:
 *
 * *********************************************************************************************************
 *      Example of the generated query:
 *           GET /api/textFile/analyze/top10 HTTP/1.1
 *           Host: localhost:8080
 *           Cache-Control: no-cache
 *           Postman-Token: c1622e87-44a2-93e1-46bf-90265b0055c6
 * *********************************************************************************************************
 *  If the file is not exist, you will get Http Status 409 - Conflict, and response body of the JSON form:
 *
 *      {
 *           "errorMessage": "The file is not exist."
 *      }
 *
 *  If the file is validated, you will get Http Status 200 - OK, and response body of the JSON form:
 *
 * {
 *       "top10": [
 *            {
 *           "книге": 6
 *           },
 *           {
 *           "людей": 5
 *           },
 *           {
 *           "прошу": 4
 *           },
 *           {
 *           "человек": 3
 *           },
 *           {
 *           "сословия": 3
 *           },
 *           {
 *           "уже": 3
 *           },
 *           {
 *           "жизнь": 3
 *           },
 *           {
 *           "будет": 3
 *           },
 *           {
 *           "замечания": 3
 *           },
 *           {
 *           "высшего": 2
 *           }
 *           ],
 *  }
 *
 *  GET request to /api/textFile/analyze/bracketCheck Verify that the brackets in the text are correctly positioned.
 *  Returns only status bracketCheck :
 *
 * *********************************************************************************************************
 *      Example of the generated query:
 *           GET /api/textFile/analyze HTTP/1.1
 *           Host: localhost:8080
 *           Cache-Control: no-cache
 *           Postman-Token: c1622e87-44a2-93e1-46bf-90265b0055c6
 * *********************************************************************************************************
 *  If file is not exist, you will get Http Status 409 - Conflict, and response body of the JSON form:
 *
 *      {
 *           "errorMessage": "The file is not exist."
 *      }
 *
 *  If the file is validated, you will get Http Status 200 - OK, and response body of the JSON form:
 *
 * {
 *           "bracketCheck": "correct"
 *  }
 *
 *
 * @author Denis Lesheniuk
 * @version 1.0
 * **/
@RestController
@RequestMapping("/api")
public class RESTApiController {
    private static Logger logger = LoggerFactory.getLogger(RESTApiController.class);

    @Autowired
    private TextService textService;

    //Uploading file
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
        try (BufferedOutputStream stream =
                     new BufferedOutputStream(new FileOutputStream(upfile))){
            logger.info(file.getOriginalFilename());
            byte[] bytes = file.getBytes();
            stream.write(bytes);
            textService.setFile(upfile);
        } catch (Exception e) {
            e.getMessage();
        }


        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    //Deleting file
    @RequestMapping(value = "/textFile", method = RequestMethod.DELETE)
        public ResponseEntity<Text> deleteText(){
            File upfile = new File("uploaded.txt");
            if(!upfile.exists()){
            logger.error("The file is not exist.");
            return new ResponseEntity(new CustomErrorType("The file is not exist."), HttpStatus.CONFLICT);
            }
            logger.info("Deleting file");

            textService.deleteFile();
        return new ResponseEntity<Text>(HttpStatus.NO_CONTENT);
    }

    //Full file analyze
    @RequestMapping(value = "textFile/analyze", method = RequestMethod.GET)
        public ResponseEntity<?> analyze(){
         File upfile = new File("uploaded.txt");
            if(!upfile.exists()){
            logger.error("The file is not exist.");
            return new ResponseEntity(new CustomErrorType("The file is not exist, please add the file."), HttpStatus.CONFLICT);
        }
            logger.info("analyzing");
           Analyze analyze = textService.fullTextAnalyze();
        return new ResponseEntity<Analyze>(analyze, HttpStatus.OK);
    }

    //Top 10 analyze
    @RequestMapping(value = "textFile/analyze/top10")
        public ResponseEntity<?> top10text(){
        File upfile = new File("uploaded.txt");
        if(!upfile.exists()){
            logger.error("The file is not exist.");
            return new ResponseEntity(new CustomErrorType("The file is not exist, please add the file."), HttpStatus.CONFLICT);
        }
        logger.info("Top 10 Text Analyze");
        Analyze analyze = textService.top10TextAnalyze();
        return new ResponseEntity<List>(analyze.getTop10(), HttpStatus.OK);
        }

    //BracketCheck analyze
    @RequestMapping(value = "textFile/analyze/bracketCheck")
    public ResponseEntity<?> bracketCheck(){
        File upfile = new File("uploaded.txt");
        if(!upfile.exists()){
            logger.error("The file is not exist.");
            return new ResponseEntity(new CustomErrorType("The file is not exist, please add the file."), HttpStatus.CONFLICT);
        }
        logger.info("Top 10 Text Analyze");
        Analyze analyze = textService.bracketCheckTextAnalyze();
        return new ResponseEntity<String>(analyze.getBracketCheck(), HttpStatus.OK);
    }
    }


