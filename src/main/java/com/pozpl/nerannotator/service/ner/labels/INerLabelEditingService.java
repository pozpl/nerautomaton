package com.pozpl.nerannotator.service.ner.labels;

import com.pozpl.nerannotator.persistence.model.job.LabelingJob;
import com.pozpl.nerannotator.service.exceptions.NerServiceException;

import java.util.List;
import java.util.Optional;

public interface INerLabelEditingService {

	/**
	 * Get labels for job
	 * @param labelingJob
	 * @return
	 */
	List<NerLabelDto> listLabelsAvailableForTask(LabelingJob labelingJob) throws NerServiceException;

	/**
	 * Get labeling Dto by Id and ensure that it belongs to task
	 * @param id
	 * @return
	 */
	Optional<NerLabelDto> getById(Long id,
								  LabelingJob labelingJob) throws NerServiceException;



	/**
	 * Save Job
	 * @param nerLabelDto
	 * @param labelingJob
	 * @return
	 * @throws NerServiceException
	 */
	NerLabelEditStatusDto saveLabel(NerLabelDto nerLabelDto, LabelingJob labelingJob) throws NerServiceException;

	/**
	 * Delete labeling task by id
	 *
	 * @throws NerServiceException
	 */
	void deleteLabel(LabelingJob labelingJob, Integer labelingTaskId) throws NerServiceException;
}
