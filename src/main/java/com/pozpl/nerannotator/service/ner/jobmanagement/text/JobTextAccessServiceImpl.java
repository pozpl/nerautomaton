package com.pozpl.nerannotator.service.ner.jobmanagement.text;

import com.pozpl.nerannotator.persistence.dao.job.LabelingJobsRepository;
import com.pozpl.nerannotator.persistence.dao.ner.NerJobTextItemRepository;
import com.pozpl.nerannotator.persistence.model.User;
import com.pozpl.nerannotator.persistence.model.job.LabelingJob;
import com.pozpl.nerannotator.persistence.model.ner.NerJobTextItem;
import com.pozpl.nerannotator.service.exceptions.NerServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class JobTextAccessServiceImpl implements IJobTextAccessService {

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
	public Page<JobTextDto> getTextForJob(final Integer jobId,
										  final Integer page,
										  final User jobOwner) throws NerServiceException {
		try {
			final Optional<LabelingJob> jobOpt = labelingJobsRepository.findByIdAndOwner(Long.valueOf(jobId), jobOwner);

			if(jobOpt.isPresent()){
				final LabelingJob job = jobOpt.get();
				if(! job.getOwner().equals(jobOwner)){
					 throw new NerServiceException("Access violation for Ner Job " + jobId + " and user: " + jobOwner.getUsername());
				}

				final Page<NerJobTextItem> textItems = this.nerJobTextItemRepository.getForJob(job,
									PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "id")));
				return textItems.map(textItem -> JobTextDto.builder()
						.id(textItem.getId().intValue())
						.text(textItem.getText())
						.jobId(job.getId().intValue())
						.build()
				);
			}

			return Page.empty();
		}catch (Exception e){
			throw new NerServiceException(e);
		}
	}
}
