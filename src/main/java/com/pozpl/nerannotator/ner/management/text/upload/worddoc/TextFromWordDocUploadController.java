package com.pozpl.nerannotator.ner.management.text.upload.worddoc;

import auth.dao.model.User;
import com.pozpl.nerannotator.ner.management.text.upload.NerTextUploadResultDto;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.shared.exceptions.NerWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ner/texts/word-doc")
public class TextFromWordDocUploadController {

    private final ITextWordDocUploadService wordDocUploadService;

    @Autowired
    public TextFromWordDocUploadController(ITextWordDocUploadService wordDocUploadService) {
        this.wordDocUploadService = wordDocUploadService;
    }

    @PostMapping("/upload")
    public NerTextUploadResultDto uploadTextsCsv(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("jobId")Integer jobId,
                                                 User user) {
        try {
            return wordDocUploadService.processFile(file, jobId, user);
        }catch (NerServiceException e){
            throw new NerWebException(e);
        }
    }


}
