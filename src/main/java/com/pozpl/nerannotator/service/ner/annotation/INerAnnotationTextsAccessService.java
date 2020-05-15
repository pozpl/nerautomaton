package com.pozpl.nerannotator.service.ner.annotation;

import com.pozpl.nerannotator.persistence.model.User;
import com.pozpl.nerannotator.service.exceptions.NerServiceException;
import com.pozpl.nerannotator.service.util.PageDto;

public interface INerAnnotationTextsAccessService {

	PageDto<NerAnnotationTextDto> getNextUnprocessed(Integer jobId,
													 User user) throws NerServiceException;

	PageDto<NerAnnotationTextDto> getProcessed(Integer jobId,
											User user,
											Integer page) throws NerServiceException;
}
