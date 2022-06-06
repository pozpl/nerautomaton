package com.pozpl.nerannotator.ner.impl.management.text.upload.csv;


import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.nerannotator.ner.impl.management.text.upload.NerTextUploadResultDto;
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
	NerTextUploadResultDto processCsv(MultipartFile file,
									  Integer jobId,
									  UserIntDto user) throws NerServiceException;

}
