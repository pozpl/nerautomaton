package com.pozpl.nerannotator.service.ner.annotation;

import com.pozpl.nerannotator.persistence.model.User;
import com.pozpl.nerannotator.service.exceptions.NerServiceException;
import org.springframework.data.domain.Page;

public interface INerAnnotationTextsAccessService {

	Page<NerAnnotationTextDto> getNextUnprocessed(Integer jobId,
											User user) throws NerServiceException;

	Page<NerAnnotationTextDto> getProcessed(Integer jobId,
											User user,
											Integer page) throws NerServiceException;
}
