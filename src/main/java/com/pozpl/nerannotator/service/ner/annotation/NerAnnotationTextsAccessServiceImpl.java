package com.pozpl.nerannotator.service.ner.annotation;

import com.pozpl.nerannotator.persistence.model.User;
import com.pozpl.nerannotator.service.exceptions.NerServiceException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NerAnnotationTextsAccessServiceImpl implements INerAnnotationTextsAccessService {



	@Override
	public NerAnnotationTextDto getNextUnprocessed(Integer jobId, User user) throws NerServiceException {
		return null;
	}

	@Override
	public Page<NerAnnotationTextDto> getProcessed(Integer jobId, User user, Integer page) throws NerServiceException {
		return null;
	}
}
