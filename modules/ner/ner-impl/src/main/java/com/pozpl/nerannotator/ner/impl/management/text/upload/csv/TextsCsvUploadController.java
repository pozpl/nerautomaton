package com.pozpl.nerannotator.ner.impl.management.text.upload.csv;

import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.nerannotator.ner.impl.management.text.upload.NerTextUploadResultDto;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.shared.exceptions.NerWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ner/texts/csv")
public class TextsCsvUploadController {

	private final ITextCsvUploadService textCsvUploadService;

	@Autowired
	public TextsCsvUploadController(ITextCsvUploadService textCsvUploadService) {
		this.textCsvUploadService = textCsvUploadService;
	}

	@PostMapping("/upload")
	public NerTextUploadResultDto uploadTextsCsv(@RequestParam("file") MultipartFile file,
												 @RequestParam("jobId")Integer jobId,
												 UserIntDto user) {
		try {
			return textCsvUploadService.processCsv(file, jobId, user);
		}catch (NerServiceException e){
			throw new NerWebException(e);
		}
	}


}
