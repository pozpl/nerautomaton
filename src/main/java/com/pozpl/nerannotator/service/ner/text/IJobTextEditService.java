package com.pozpl.nerannotator.service.ner.text;

import com.pozpl.nerannotator.persistence.model.User;
import com.pozpl.nerannotator.service.exceptions.NerServiceException;

/**
 * Job text editing service 
 */
public interface IJobTextEditService {

	/**
	 * Get job by Id
	 * @param id
	 * @param user
	 * @return
	 * @throws NerServiceException
	 */
	JobTextDto getById(Integer id,
					   User user) throws NerServiceException;


	/**
	 * Save job text information provided by dto
	 * @param jobTextDto
	 * @param user
	 * @return
	 * @throws NerServiceException
	 */
	JobTextEditStatusDto save(JobTextDto jobTextDto,
							  User user) throws NerServiceException;


	/**
	 * Delete job text
	 * @param jobTextId
	 * @param user
	 * @throws NerServiceException
	 */
	void deleteJobText(Integer jobTextId,
					   User user) throws NerServiceException;

}
