package com.pozpl.nerannotator.ner.impl.annotation.tasks;

import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.nerannotator.ner.impl.dao.model.job.LabelingJob;
import com.pozpl.nerannotator.ner.impl.dao.model.user.UserId;
import com.pozpl.nerannotator.ner.impl.dao.repo.job.LabelingJobsRepository;
import com.pozpl.nerannotator.ner.impl.dao.repo.text.UserTextProcessingResultRepository;
import com.pozpl.nerannotator.ner.impl.management.job.NerJobDto;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.shared.pagination.PageDto;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AnnotationTaskListServiceImpl implements IAnnotationTaskListService {

	private final LabelingJobsRepository labelingJobsRepository;
	private final UserTextProcessingResultRepository userTextProcessingResultRepository;

	@Autowired
	public AnnotationTaskListServiceImpl(LabelingJobsRepository labelingJobsRepository,
										 UserTextProcessingResultRepository userTextProcessingResultRepository) {
		this.labelingJobsRepository = labelingJobsRepository;
		this.userTextProcessingResultRepository = userTextProcessingResultRepository;
	}

	/**
	 * List annotation job available to the annotator
	 *
	 * @param user
	 * @param page
	 * @return
	 * @throws NerServiceException
	 */
	@Override
	public PageDto<UserNerTaskDescriptionDto> listAvailableJobs(UserIntDto user, Integer page) throws NerServiceException {
		try {
			final Integer adjustedPage = page != null && page >= 0 ? page : 0;
			Page<LabelingJob> userJobs = labelingJobsRepository.getJobsForOwner(UserId.of(user), PageRequest.of(adjustedPage, 20, Sort.by(Sort.Direction.DESC, "created")));

			final List<UserNerTaskDescriptionDto> jobs = userJobs.getContent().stream()
					.map(nerJob -> Try.of(() -> toDto(nerJob, user)))
					.map(nerJobTry -> nerJobTry.getOrElse(new UserNerTaskDescriptionDto(new NerJobDto(), 0, 0)))
					.collect(Collectors.toList());

			return new PageDto<>(page, new Long(userJobs.getTotalElements()).intValue(), 20, jobs);
		} catch (Exception e) {
			throw new NerServiceException(e);
		}
	}


	private UserNerTaskDescriptionDto toDto(final LabelingJob labelingJob, UserIntDto user) {

		final Integer countProcessed = this.userTextProcessingResultRepository.countProcessed(UserId.of(user), labelingJob);
		final Integer countUnprocessed = this.userTextProcessingResultRepository.countUnprocessed(UserId.of(user), labelingJob);

		final NerJobDto jobDto = NerJobDto.builder()
				.id(labelingJob.getId().intValue())
				.name(labelingJob.getName())
				.created(labelingJob.getCreated().getTime())
				.build();

		return new UserNerTaskDescriptionDto(jobDto, countProcessed, countUnprocessed);
	}
}
