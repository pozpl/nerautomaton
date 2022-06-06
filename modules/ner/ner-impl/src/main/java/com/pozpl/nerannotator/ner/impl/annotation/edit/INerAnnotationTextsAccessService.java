package com.pozpl.nerannotator.ner.impl.annotation.edit;

import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.shared.pagination.PageDto;

import java.util.List;

public interface INerAnnotationTextsAccessService {

	List<NerTextAnnotationDto> getNextUnprocessed(Integer jobId,
												  UserIntDto user) throws NerServiceException;

	PageDto<NerTextAnnotationDto> getProcessed(Integer jobId,
											   UserIntDto user,
											   Integer page) throws NerServiceException;
}
