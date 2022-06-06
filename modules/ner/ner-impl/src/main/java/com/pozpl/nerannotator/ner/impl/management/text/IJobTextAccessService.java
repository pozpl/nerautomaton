package com.pozpl.nerannotator.ner.impl.management.text;

import com.pozpl.neraannotator.user.api.UserIntDto;
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
									  UserIntDto accessor) throws NerServiceException;

}
