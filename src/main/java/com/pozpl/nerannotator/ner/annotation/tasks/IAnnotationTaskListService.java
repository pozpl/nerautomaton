package com.pozpl.nerannotator.ner.annotation.tasks;

import com.pozpl.nerannotator.auth.dao.model.User;
import com.pozpl.nerannotator.ner.management.job.NerJobDto;
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
	PageDto<NerJobDto> listAvailableJobs(User user, Integer page) throws NerServiceException;

}
