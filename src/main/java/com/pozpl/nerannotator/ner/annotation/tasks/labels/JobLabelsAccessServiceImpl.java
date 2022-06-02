package com.pozpl.nerannotator.ner.annotation.tasks.labels;

import auth.dao.model.User;
import com.pozpl.nerannotator.ner.annotation.rights.IUserJobTasksRightsService;
import com.pozpl.nerannotator.ner.dao.model.job.LabelingJob;
import com.pozpl.nerannotator.ner.dao.model.text.NerLabel;
import com.pozpl.nerannotator.ner.dao.repo.job.LabelingJobsRepository;
import com.pozpl.nerannotator.ner.dao.repo.text.NerLabelsRepository;
import com.pozpl.nerannotator.ner.management.labels.NerLabelDto;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class JobLabelsAccessServiceImpl implements IJobLabelsAccessService {

	private final IUserJobTasksRightsService userJobTasksRightsService;
	private final NerLabelsRepository nerLabelsRepository;
	private final LabelingJobsRepository labelingJobsRepository;

	@Autowired
	public JobLabelsAccessServiceImpl(IUserJobTasksRightsService userJobTasksRightsService,
									  NerLabelsRepository nerLabelsRepository,
									  LabelingJobsRepository labelingJobsRepository) {
		this.userJobTasksRightsService = userJobTasksRightsService;
		this.nerLabelsRepository = nerLabelsRepository;
		this.labelingJobsRepository = labelingJobsRepository;
	}

	/**
	 * Get labels for user
	 *
	 * @param jobId
	 * @param user
	 * @return
	 * @throws NerServiceException
	 */
	@Override
	public List<NerLabelDto> getLabels(final Integer jobId, final User user) throws NerServiceException {
		try {
			final Optional<LabelingJob> jobOpt = labelingJobsRepository.findById(Long.valueOf(jobId));

			if(! jobOpt.isPresent()){
				return Collections.emptyList();
			}
			final LabelingJob job = jobOpt.get();

			if(userJobTasksRightsService.doesUserHaveAccessToJobTasks(user, job)){
				final List<NerLabel> labels = nerLabelsRepository.getForJob(job);
				return labels.stream()
						.map(this::toDto)
						.collect(Collectors.toList());
			}

			return Collections.emptyList();
		}catch (Exception e){
			throw new NerServiceException(e);
		}
	}

	private NerLabelDto toDto(final NerLabel nerJobAvailableEntity) {
		return NerLabelDto.builder()
				.id(nerJobAvailableEntity.getId().intValue())
				.name(nerJobAvailableEntity.getName())
				.description(nerJobAvailableEntity.getDescription())
				.build();

	}
}
