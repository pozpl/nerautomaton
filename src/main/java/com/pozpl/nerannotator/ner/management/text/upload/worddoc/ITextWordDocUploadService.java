package com.pozpl.nerannotator.ner.management.text.upload.worddoc;

import com.pozpl.nerannotator.auth.dao.model.User;
import com.pozpl.nerannotator.ner.management.text.upload.NerTextUploadResultDto;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import org.springframework.web.multipart.MultipartFile;

public interface ITextWordDocUploadService {


    NerTextUploadResultDto processFile(MultipartFile file,
                                       Integer jobId,
                                       User user) throws NerServiceException;

}
