package com.pozpl.nerannotator.ner.impl.management.text;

import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.nerannotator.ner.impl.dao.model.job.LabelingJob;
import com.pozpl.nerannotator.ner.impl.dao.model.text.NerJobTextItem;
import com.pozpl.nerannotator.ner.impl.dao.model.user.UserId;
import com.pozpl.nerannotator.ner.impl.dao.repo.job.LabelingJobsRepository;
import com.pozpl.nerannotator.ner.impl.dao.repo.text.NerJobTextItemRepository;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.shared.pagination.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class JobTextAccessServiceImpl implements IJobTextAccessService {

	private static final int PER_PAGE = 20;

	private final NerJobTextItemRepository nerJobTextItemRepository;
	private final LabelingJobsRepository labelingJobsRepository;

	@Autowired
	public JobTextAccessServiceImpl(NerJobTextItemRepository nerJobTextItemRepository,
									LabelingJobsRepository labelingJobsRepository) {
		this.nerJobTextItemRepository = nerJobTextItemRepository;
		this.labelingJobsRepository = labelingJobsRepository;
	}

	/**
	 * List texts for job
	 *
	 * @param jobId - job id
	 * @param page - page
	 * @return returns job texts pagination
	 * @throws NerServiceException - throws exception if something happens
	 */
	@Override
	public PageDto<JobTextDto> getTextForJob(final Integer jobId,
											 final Integer page,
											 final UserIntDto jobOwner) throws NerServiceException {
		try {
			final Optional<LabelingJob> jobOpt = labelingJobsRepository.findByIdAndOwner(Long.valueOf(jobId), UserId.of(jobOwner));

			if (jobOpt.isPresent()) {
				final LabelingJob job = jobOpt.get();
				if (!job.getOwner().getId().equals(jobOwner.getId())) {
					throw new NerServiceException("Access violation for Ner Job " + jobId + " and user: " + jobOwner.getUsername());
				}

				int adjustedPage = page >= 0 ? page : 0;
				final Page<NerJobTextItem> textItemsPage = this.nerJobTextItemRepository.getForJob(job,
						PageRequest.of(adjustedPage, PER_PAGE, Sort.by(Sort.Direction.DESC, "id")));
				final List<JobTextDto> textItems = textItemsPage.getContent().stream()
						.map(textItem -> JobTextDto.builder()
								.id(textItem.getId().intValue())
								.text(textItem.getText())
								.jobId(job.getId().intValue())
								.build()
						).collect(Collectors.toList());
				return new PageDto<>(page, Long.valueOf(textItemsPage.getTotalElements()).intValue(), PER_PAGE, textItems);
			}

			return PageDto.empty();
		} catch (Exception e) {
			throw new NerServiceException(e);
		}
	}
}
