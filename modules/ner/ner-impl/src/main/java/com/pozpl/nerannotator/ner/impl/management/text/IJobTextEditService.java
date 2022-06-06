package com.pozpl.nerannotator.ner.impl.management.text;

import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;

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
					   UserIntDto user) throws NerServiceException;


	/**
	 * Save job text information provided by dto
	 * @param jobTextDto
	 * @param user
	 * @return
	 * @throws NerServiceException
	 */
	JobTextEditStatusDto save(JobTextDto jobTextDto,
							  UserIntDto user) throws NerServiceException;


	/**
	 * Delete job text
	 * @param jobTextId
	 * @param user
	 * @throws NerServiceException
	 */
	void deleteJobText(Integer jobTextId,
					   UserIntDto user) throws NerServiceException;

}
