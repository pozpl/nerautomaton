package com.pozpl.nerannotator.ner.management.text.csvupload;


import com.pozpl.nerannotator.auth.dao.model.User;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import org.springframework.web.multipart.MultipartFile;

public interface ITextCsvUploadService {

	/**
	 * Process CSV file and add text from it to the presented Job
	 * @param file
	 * @param user
	 * @return
	 * @throws NerServiceException
	 */
	NerTextCsvUploadResultDto processCsv(MultipartFile file,
										 Integer jobId,
										 User user) throws NerServiceException;

}
