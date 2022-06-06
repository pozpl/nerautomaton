package com.pozpl.nerannotator.ner.impl.management.job;

import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.shared.pagination.PageDto;

import java.util.Optional;

public interface INerJobService {

	/**
	 * Get Job by Id
	 * @param id
	 * @param user
	 * @return
	 * @throws NerServiceException
	 */
	Optional<NerJobDto> getJobById(Integer id, UserIntDto user) throws NerServiceException;

	/**
	 * Get list of jobs
	 * @param owner
	 * @param page
	 * @return
	 * @throws NerServiceException
	 */
	PageDto<NerJobDto> getJobsForOwner(UserIntDto owner, Integer page) throws NerServiceException;

	/**
	 * Save Job
	 * @param nerJobDto
	 * @param user
	 * @return
	 * @throws NerServiceException
	 */
	NerJobSaveStatusDto saveJob(NerJobDto nerJobDto, UserIntDto user) throws NerServiceException;

	/**
	 * Delete job by id
	 * @param user
	 * @param jobId
	 * @throws NerServiceException
	 */
	boolean deleteJob(UserIntDto user, Integer jobId) throws NerServiceException;

}
