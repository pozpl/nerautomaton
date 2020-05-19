package com.pozpl.nerannotator.ner.management.labels;

import com.pozpl.nerannotator.ner.dao.model.job.LabelingJob;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;

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
	Optional<NerLabelDto> getById(Integer id,
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
	 * Save all labels and delete not labels not present in the list
	 * @param labelingJob
	 * @param labelDtos
	 * @return
	 * @throws NerServiceException
	 */
	List<NerLabelDto> saveAllLabelsForJob(final LabelingJob labelingJob,
										  final List<NerLabelDto> labelDtos) throws NerServiceException;

	/**
	 * Delete labeling task by id
	 *
	 * @throws NerServiceException
	 */
	void deleteLabel(LabelingJob labelingJob, Integer labelingTaskId) throws NerServiceException;
}
