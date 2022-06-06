package com.pozpl.nerannotator.ner.impl.annotation.rights;

import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.nerannotator.ner.impl.dao.model.job.LabelingJob;
import com.pozpl.nerannotator.ner.impl.dao.model.user.UserId;
import com.pozpl.nerannotator.ner.impl.dao.repo.job.LabelingJobsRepository;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
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
	public boolean doesUserHaveAccessToJobTasks(final UserIntDto user,
												final LabelingJob job) throws NerServiceException {
		final UserId jobOwner = this.labelingJobsRepository.getOwnerForJob(job);

		//Very trivial rules at first only Owner can access Job Tasks
		return jobOwner.getId().equals(user.getId());
	}
}
