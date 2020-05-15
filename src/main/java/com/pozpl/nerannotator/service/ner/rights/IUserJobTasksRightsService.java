package com.pozpl.nerannotator.service.ner.rights;

import com.pozpl.nerannotator.persistence.model.User;
import com.pozpl.nerannotator.persistence.model.job.LabelingJob;
import com.pozpl.nerannotator.service.exceptions.NerServiceException;

public interface IUserJobTasksRightsService {

	boolean doesUserHaveAccessToJobTasks(User user,
										 LabelingJob job) throws NerServiceException;
}
