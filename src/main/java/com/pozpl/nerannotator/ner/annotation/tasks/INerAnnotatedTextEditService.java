package com.pozpl.nerannotator.ner.annotation.tasks;

import com.pozpl.nerannotator.auth.dao.model.User;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;

public interface INerAnnotatedTextEditService {

	NerTextAnnotationEditResultDto save(NerTextAnnotationDto annotationTextDto,
							  User user) throws NerServiceException;

}
