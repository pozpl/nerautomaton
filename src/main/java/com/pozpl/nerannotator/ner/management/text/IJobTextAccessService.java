package com.pozpl.nerannotator.ner.management.text;

import com.pozpl.nerannotator.auth.dao.model.User;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.shared.pagination.PageDto;
import org.springframework.data.domain.Page;

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
