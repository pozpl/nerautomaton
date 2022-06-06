package com.pozpl.nerannotator.ner.impl.annotation.rights;

import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.nerannotator.ner.impl.dao.model.job.LabelingJob;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;

public interface IUserJobTasksRightsService {

	boolean doesUserHaveAccessToJobTasks(UserIntDto user,
										 LabelingJob job) throws NerServiceException;
}
