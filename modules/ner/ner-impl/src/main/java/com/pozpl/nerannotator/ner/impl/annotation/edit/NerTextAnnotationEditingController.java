package com.pozpl.nerannotator.ner.impl.annotation.edit;

import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.shared.exceptions.NerWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ner/annotation/text/annotation")
public class NerTextAnnotationEditingController {

	private final INerAnnotatedTextEditService nerAnnotatedTextEditService;

	@Autowired
	public NerTextAnnotationEditingController(INerAnnotatedTextEditService nerAnnotatedTextEditService) {
		this.nerAnnotatedTextEditService = nerAnnotatedTextEditService;
	}


	@PostMapping("/save")
	public NerTextAnnotationEditResultDto save(@RequestBody NerTextAnnotationDto annotationDto,
											   UserIntDto user) {
		try{
			return nerAnnotatedTextEditService.save(annotationDto, user);
		}catch (NerServiceException e){
			throw new NerWebException(e);
		}
	}

}
