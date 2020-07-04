package com.pozpl.nerannotator.ner.management.text.csvupload;

import com.pozpl.nerannotator.auth.dao.model.User;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.shared.exceptions.NerWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/ner/texts/csv")
public class TextsCsvUploadController {

	private final ITextCsvUploadService textCsvUploadService;

	@Autowired
	public TextsCsvUploadController(ITextCsvUploadService textCsvUploadService) {
		this.textCsvUploadService = textCsvUploadService;
	}

	@PostMapping("/upload")
	public NerTextCsvUploadResultDto uploadTextsCsv(@RequestParam("file") MultipartFile file,
													@RequestParam("id")Integer jobId,
													User user) {
		try {
			return textCsvUploadService.processCsv(file, jobId, user);
		}catch (NerServiceException e){
			throw new NerWebException(e);
		}
	}


}
