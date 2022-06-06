package com.pozpl.nerannotator.ner.impl.management.text.upload.txtfile;

import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.nerannotator.ner.impl.management.text.upload.NerTextUploadResultDto;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import org.springframework.web.multipart.MultipartFile;

public interface ITxtFileUploadService {

    NerTextUploadResultDto processFile(MultipartFile file,
                                       Integer jobId,
                                       UserIntDto user) throws NerServiceException;

}
