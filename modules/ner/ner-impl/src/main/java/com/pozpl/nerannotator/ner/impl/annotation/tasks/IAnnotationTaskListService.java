package com.pozpl.nerannotator.ner.impl.annotation.tasks;

import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.shared.pagination.PageDto;

public interface IAnnotationTaskListService {

	/**
	 * List annotation job available to the annotator
	 * @param user
	 * @param page
	 * @return
	 * @throws NerServiceException
	 */
	PageDto<UserNerTaskDescriptionDto> listAvailableJobs(UserIntDto user, Integer page) throws NerServiceException;

}
