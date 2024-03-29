package com.pozpl.nerannotator.ner.impl.annotation.edit;

import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.shared.exceptions.NerWebException;
import com.pozpl.nerannotator.shared.pagination.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ner/annotation/text/tasks")
public class AnnotationTextsAccessController {

	private final INerAnnotationTextsAccessService textsAccessService;

	@Autowired
	public AnnotationTextsAccessController(INerAnnotationTextsAccessService textsAccessService) {
		this.textsAccessService = textsAccessService;
	}

	@GetMapping("/get/unprocessed")
	public List<NerTextAnnotationDto> getUnprocessed(@RequestParam(name = "jobId") final Integer jobId,
													 final UserIntDto user) {
		try{
			return this.textsAccessService.getNextUnprocessed(jobId, user);
		} catch (NerServiceException e){
			throw new NerWebException(e);
		}
	}

	@GetMapping("/list/processed")
	public PageDto<NerTextAnnotationDto> listProcessed(@RequestParam(name = "jobId") final Integer jobId,
													   @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
													   final UserIntDto user){
		try{
			return this.textsAccessService.getProcessed(jobId, user, page);
		}catch (NerServiceException e){
			throw new NerWebException(e);
		}
	}

}
