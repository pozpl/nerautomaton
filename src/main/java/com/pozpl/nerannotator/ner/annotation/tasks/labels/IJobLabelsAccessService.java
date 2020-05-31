package com.pozpl.nerannotator.ner.annotation.tasks.labels;

import com.pozpl.nerannotator.auth.dao.model.User;
import com.pozpl.nerannotator.ner.management.labels.NerLabelDto;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;

import java.util.List;

public interface IJobLabelsAccessService {

	/**
	 * Get labels for user
	 * @param jobId
	 * @param user
	 * @return
	 * @throws NerServiceException
	 */
	List<NerLabelDto> getLabels(Integer jobId, User user) throws NerServiceException;

}
