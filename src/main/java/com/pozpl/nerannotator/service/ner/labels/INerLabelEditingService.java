package com.pozpl.nerannotator.service.ner.labels;

import com.pozpl.nerannotator.persistence.model.job.LabelingTask;
import com.pozpl.nerannotator.service.exceptions.NerServiceException;

import java.util.List;
import java.util.Optional;

public interface INerLabelEditingService {

	/**
	 * Get labels for job
	 * @param labelingTask
	 * @return
	 */
	List<NerLabelDto> listLabelsAvailableForTask(LabelingTask labelingTask) throws NerServiceException;

	/**
	 * Get labeling Dto by Id and ensure that it belongs to task
	 * @param id
	 * @return
	 */
	Optional<NerLabelDto> getById(Integer id,
								  LabelingTask labelingTask) throws NerServiceException;



	/**
	 * Save Job
	 * @param nerLabelDto
	 * @param labelingTask
	 * @return
	 * @throws NerServiceException
	 */
	NerLabelDto saveLabel(NerLabelDto nerLabelDto, LabelingTask labelingTask) throws NerServiceException;

	/**
	 * Delete labeling task by id
	 *
	 * @throws NerServiceException
	 */
	void deleteLabel(LabelingTask labelingTask, Integer labelingTaskId) throws NerServiceException;
}
