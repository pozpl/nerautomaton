package com.pozpl.nerannotator.ner.management.job;

import com.pozpl.nerannotator.auth.dao.model.User;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface INerJobService {

	/**
	 * Get Job by Id
	 * @param id
	 * @param user
	 * @return
	 * @throws NerServiceException
	 */
	Optional<NerJobDto> getJobById(Integer id, User user) throws NerServiceException;

	/**
	 * Get list of jobs
	 * @param owner
	 * @param page
	 * @return
	 * @throws NerServiceException
	 */
	Page<NerJobDto> getJobsForOwner(User owner, Integer page) throws NerServiceException;

	/**
	 * Save Job
	 * @param nerJobDto
	 * @param user
	 * @return
	 * @throws NerServiceException
	 */
	NerJobSaveStatusDto saveJob(NerJobDto nerJobDto, User user) throws NerServiceException;

	/**
	 * Delete job by id
	 * @param user
	 * @param jobId
	 * @throws NerServiceException
	 */
	void deleteJob(User user, Integer jobId) throws NerServiceException;

}