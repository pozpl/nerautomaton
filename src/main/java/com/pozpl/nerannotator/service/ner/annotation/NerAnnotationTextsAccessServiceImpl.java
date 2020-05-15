package com.pozpl.nerannotator.service.ner.annotation;

import com.pozpl.nerannotator.persistence.dao.job.LabelingJobsRepository;
import com.pozpl.nerannotator.persistence.dao.ner.NerJobTextItemRepository;
import com.pozpl.nerannotator.persistence.dao.ner.NerLabelsRepository;
import com.pozpl.nerannotator.persistence.dao.ner.UserTextProcessingResultRepository;
import com.pozpl.nerannotator.persistence.model.User;
import com.pozpl.nerannotator.persistence.model.job.LabelingJob;
import com.pozpl.nerannotator.persistence.model.ner.NerJobTextItem;
import com.pozpl.nerannotator.service.exceptions.NerServiceException;
import com.pozpl.nerannotator.service.ner.rights.IUserJobTasksRightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class NerAnnotationTextsAccessServiceImpl implements INerAnnotationTextsAccessService {

	private final UserTextProcessingResultRepository processingResultRepository;
	private final NerLabelsRepository labelsRepository;
	private final LabelingJobsRepository labelingJobsRepository;
	private final IUserJobTasksRightsService userJobTasksRightsService;

	@Autowired
	public NerAnnotationTextsAccessServiceImpl(UserTextProcessingResultRepository processingResultRepository,
											   NerLabelsRepository labelsRepository,
											   LabelingJobsRepository labelingJobsRepository,
											   IUserJobTasksRightsService userJobTasksRightsService) {
		this.processingResultRepository = processingResultRepository;
		this.labelsRepository = labelsRepository;
		this.labelingJobsRepository = labelingJobsRepository;
		this.userJobTasksRightsService = userJobTasksRightsService;
	}

	@Override
	public Page<NerAnnotationTextDto> getNextUnprocessed(final Integer jobId,
														 final User user) throws NerServiceException {
		try {
			final Optional<LabelingJob> labelingJobOpt = this.labelingJobsRepository.findById(Long.valueOf(jobId));

			if(! labelingJobOpt.isPresent()){
				 return Page.empty();
			}

			final LabelingJob job = labelingJobOpt.get();
			final boolean canUserAccessTheJob = this.userJobTasksRightsService.doesUserHaveAccessToJobTasks(user, job);

			if(! canUserAccessTheJob){
				return Page.empty();
			}

			final Page<NerJobTextItem> unprocessedTextItems = processingResultRepository.getUnprocessed(user, job,
									PageRequest.of(1, 20, Sort.by(Sort.Direction.DESC, "created")));



			return null;
		}catch (Exception e){
			throw new NerServiceException(e);
		}
	}

	@Override
	public Page<NerAnnotationTextDto> getProcessed(final Integer jobId,
												   final User user,
												   final Integer page) throws NerServiceException {
		return null;
	}


}
