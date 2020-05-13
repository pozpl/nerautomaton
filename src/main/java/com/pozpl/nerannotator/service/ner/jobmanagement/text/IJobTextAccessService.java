package com.pozpl.nerannotator.service.ner.jobmanagement.text;

import com.pozpl.nerannotator.persistence.model.User;
import com.pozpl.nerannotator.service.exceptions.NerServiceException;
import org.springframework.data.domain.Page;

public interface IJobTextAccessService {

	/**
	 * List texts for job
	 * @param jobId
	 * @param page
	 * @return
	 * @throws NerServiceException
	 */
	Page<JobTextDto> getTextForJob(Integer jobId, Integer page,
								   User accessor) throws NerServiceException;

}
