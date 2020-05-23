package com.pozpl.nerannotator.ner.management.text;

import com.pozpl.nerannotator.ner.dao.repo.job.LabelingJobsRepository;
import com.pozpl.nerannotator.ner.dao.repo.text.NerJobTextItemRepository;
import com.pozpl.nerannotator.auth.dao.model.User;
import com.pozpl.nerannotator.ner.dao.model.job.LabelingJob;
import com.pozpl.nerannotator.ner.dao.model.text.NerJobTextItem;
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
	 * @param jobId
	 * @param page
	 * @return
	 * @throws NerServiceException
	 */
	@Override
	public PageDto<JobTextDto> getTextForJob(final Integer jobId,
											 final Integer page,
											 final User jobOwner) throws NerServiceException {
		try {
			final Optional<LabelingJob> jobOpt = labelingJobsRepository.findByIdAndOwner(Long.valueOf(jobId), jobOwner);

			if (jobOpt.isPresent()) {
				final LabelingJob job = jobOpt.get();
				if (!job.getOwner().equals(jobOwner)) {
					throw new NerServiceException("Access violation for Ner Job " + jobId + " and user: " + jobOwner.getUsername());
				}

				final Page<NerJobTextItem> textItemsPage = this.nerJobTextItemRepository.getForJob(job,
						PageRequest.of(0, PER_PAGE, Sort.by(Sort.Direction.DESC, "id")));
				final List<JobTextDto> textItems = textItemsPage.getContent().stream()
						.map(textItem -> JobTextDto.builder()
								.id(textItem.getId().intValue())
								.text(textItem.getText())
								.jobId(job.getId().intValue())
								.build()
						).collect(Collectors.toList());
				return new PageDto(page, textItemsPage.getSize(), PER_PAGE, textItems);
			}

			return PageDto.empty();
		} catch (Exception e) {
			throw new NerServiceException(e);
		}
	}
}
