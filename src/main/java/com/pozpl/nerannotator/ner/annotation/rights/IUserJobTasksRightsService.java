package com.pozpl.nerannotator.ner.annotation.rights;

import com.pozpl.nerannotator.auth.dao.model.User;
import com.pozpl.nerannotator.ner.dao.model.job.LabelingJob;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;

public interface IUserJobTasksRightsService {

	boolean doesUserHaveAccessToJobTasks(User user,
										 LabelingJob job) throws NerServiceException;
}
