package com.pozpl.nerannotator.ner.annotation.tasks;

import com.pozpl.nerannotator.auth.dao.model.User;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.shared.pagination.PageDto;

public interface INerAnnotationTextsAccessService {

	PageDto<NerAnnotationTextDto> getNextUnprocessed(Integer jobId,
													 User user) throws NerServiceException;

	PageDto<NerAnnotationTextDto> getProcessed(Integer jobId,
											User user,
											Integer page) throws NerServiceException;
}
