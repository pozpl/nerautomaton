package com.pozpl.nerannotator.ner.impl.annotation.tasks.labels;

import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.nerannotator.ner.impl.management.labels.NerLabelDto;
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
	List<NerLabelDto> getLabels(Integer jobId, UserIntDto user) throws NerServiceException;

}
