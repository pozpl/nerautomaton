package com.pozpl.nerannotator.ner.impl.annotation.edit;

import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;

public interface INerAnnotatedTextEditService {

	NerTextAnnotationEditResultDto save(NerTextAnnotationDto annotationTextDto,
							  UserIntDto user) throws NerServiceException;

}
