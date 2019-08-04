package com.pozpl.nerannotator.service.ner.text;

import com.pozpl.nerannotator.persistence.model.User;
import com.pozpl.nerannotator.service.exceptions.NerServiceException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class JobTextEditServiceImpl implements IJobTextEditService {


	/**
	 * Get job by Id
	 *
	 * @param id
	 * @param user
	 * @return
	 * @throws NerServiceException
	 */
	@Override
	public JobTextDto getById(final Integer id,
							  final User user) throws NerServiceException {

		return null;

	}

	/**
	 * Save job text information provided by dto
	 *
	 * @param jobTextDto
	 * @param user
	 * @return
	 * @throws NerServiceException
	 */
	@Override
	public JobTextEditStatusDto save(JobTextDto jobTextDto, User user) throws NerServiceException {
		return null;
	}

	/**
	 * Delete job text
	 *
	 * @param jobTextId
	 * @param user
	 * @throws NerServiceException
	 */
	@Override
	public void deleteJobText(Integer jobTextId, User user) throws NerServiceException {

	}
}
