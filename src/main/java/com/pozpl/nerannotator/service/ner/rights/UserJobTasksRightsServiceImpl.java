package com.pozpl.nerannotator.service.ner.rights;

import com.pozpl.nerannotator.persistence.dao.job.LabelingJobsRepository;
import com.pozpl.nerannotator.persistence.model.User;
import com.pozpl.nerannotator.persistence.model.job.LabelingJob;
import com.pozpl.nerannotator.service.exceptions.NerServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserJobTasksRightsServiceImpl implements IUserJobTasksRightsService {

	private final LabelingJobsRepository labelingJobsRepository;

	@Autowired
	public UserJobTasksRightsServiceImpl(LabelingJobsRepository labelingJobsRepository) {
		this.labelingJobsRepository = labelingJobsRepository;
	}

	@Override
	public boolean doesUserHaveAccessToJobTasks(final User user,
												final LabelingJob job) throws NerServiceException {
		final User jobOwner = this.labelingJobsRepository.getOwnerForJob(job);

		//Very trivial rules at first only Owner can access Job Tasks
		return jobOwner.equals(user);
	}
}
