package com.pozpl.nerannotator.ner.management.text.upload.txtfile;

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
@RequestMapping("/ner/texts/txt-file")
public class BulkTxtFileTextUploadController {

    private final ITxtFileUploadService txtFileUploadService;

    @Autowired
    public BulkTxtFileTextUploadController(ITxtFileUploadService txtFileUploadService) {
        this.txtFileUploadService = txtFileUploadService;
    }


    @PostMapping("/upload")
    public NerTextUploadResultDto uploadTextsCsv(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("jobId")Integer jobId,
                                                 User user) {
        try {
            return txtFileUploadService.processFile(file, jobId, user);
        }catch (NerServiceException e){
            throw new NerWebException(e);
        }
    }

}
