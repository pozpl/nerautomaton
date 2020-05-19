package com.pozpl.nerannotator.ner.annotation.tasks;

import com.pozpl.nerannotator.auth.dao.model.User;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.shared.exceptions.NerWebException;
import com.pozpl.nerannotator.shared.pagination.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ner/annotation/text/tasks")
public class AnnotationTextsAccessController {

	private final INerAnnotationTextsAccessService textsAccessService;

	@Autowired
	public AnnotationTextsAccessController(INerAnnotationTextsAccessService textsAccessService) {
		this.textsAccessService = textsAccessService;
	}

	@GetMapping("/get/unprocessed")
	public PageDto<NerAnnotationTextDto> getUnprocessed(@RequestParam(name = "jobId") final Integer jobId,
														 final User user) {
		try{
			return this.textsAccessService.getNextUnprocessed(jobId, user);
		} catch (NerServiceException e){
			throw new NerWebException(e);
		}
	}

	@GetMapping("/list/processed")
	public PageDto<NerAnnotationTextDto> listProcessed(@RequestParam(name = "jobId") final Integer jobId,
													   @RequestParam(name = "page", required = false, defaultValue = "1") final Integer page,
													   final User user){
		try{
			return this.textsAccessService.getProcessed(jobId, user, page);
		}catch (NerServiceException e){
			throw new NerWebException(e);
		}
	}

}
