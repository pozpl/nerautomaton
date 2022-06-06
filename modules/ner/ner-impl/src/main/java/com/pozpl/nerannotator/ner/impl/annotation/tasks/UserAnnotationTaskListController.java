package com.pozpl.nerannotator.ner.impl.annotation.tasks;

import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.shared.exceptions.NerWebException;
import com.pozpl.nerannotator.shared.pagination.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/ner/user/tasks")
public class UserAnnotationTaskListController {

	private final IAnnotationTaskListService annotationTaskListService;

	@Autowired
	public UserAnnotationTaskListController(IAnnotationTaskListService annotationTaskListService) {
		this.annotationTaskListService = annotationTaskListService;
	}

	@GetMapping("/list")
	public PageDto<UserNerTaskDescriptionDto> list(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
							UserIntDto user) {
		try{
			return annotationTaskListService.listAvailableJobs(user, page);
		}   catch (NerServiceException e){
			throw new NerWebException(e);
		}
	}
}
