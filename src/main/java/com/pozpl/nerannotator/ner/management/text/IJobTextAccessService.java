package com.pozpl.nerannotator.ner.management.text;

import auth.dao.model.User;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.shared.pagination.PageDto;

public interface IJobTextAccessService {

	/**
	 * List texts for job
	 * @param jobId
	 * @param page
	 * @return
	 * @throws NerServiceException
	 */
	PageDto<JobTextDto> getTextForJob(Integer jobId, Integer page,
									  User accessor) throws NerServiceException;

}
