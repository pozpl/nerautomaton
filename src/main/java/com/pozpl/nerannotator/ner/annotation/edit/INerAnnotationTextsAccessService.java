package com.pozpl.nerannotator.ner.annotation.edit;

import com.pozpl.nerannotator.auth.dao.model.User;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.shared.pagination.PageDto;

public interface INerAnnotationTextsAccessService {

	PageDto<NerTextAnnotationDto> getNextUnprocessed(Integer jobId,
													 User user) throws NerServiceException;

	PageDto<NerTextAnnotationDto> getProcessed(Integer jobId,
											   User user,
											   Integer page) throws NerServiceException;
}
