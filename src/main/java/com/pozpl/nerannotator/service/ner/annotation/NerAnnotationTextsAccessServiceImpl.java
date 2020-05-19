package com.pozpl.nerannotator.service.ner.annotation;

import com.pozpl.nerannotator.persistence.dao.job.LabelingJobsRepository;
import com.pozpl.nerannotator.persistence.dao.ner.UserTextProcessingResultRepository;
import com.pozpl.nerannotator.persistence.model.User;
import com.pozpl.nerannotator.persistence.model.job.LabelingJob;
import com.pozpl.nerannotator.persistence.model.ner.NerJobTextItem;
import com.pozpl.nerannotator.persistence.model.ner.UserNerTextProcessingResult;
import com.pozpl.nerannotator.service.exceptions.NerServiceException;
import com.pozpl.nerannotator.service.ner.rights.IUserJobTasksRightsService;
import com.pozpl.nerannotator.service.ner.text.INerAnnotatedTextParsingService;
import com.pozpl.nerannotator.service.ner.text.IRawTextPreprocessorForNer;
import com.pozpl.nerannotator.service.ner.text.TaggedTermDto;
import com.pozpl.nerannotator.service.util.PageDto;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NerAnnotationTextsAccessServiceImpl implements INerAnnotationTextsAccessService {

	private final UserTextProcessingResultRepository processingResultRepository;
	private final LabelingJobsRepository labelingJobsRepository;
	private final IUserJobTasksRightsService userJobTasksRightsService;
	private final IRawTextPreprocessorForNer textPreprocessorForNer;
	private final INerAnnotatedTextParsingService nerAnnotatedTextParsingService;

	@Autowired
	public NerAnnotationTextsAccessServiceImpl(UserTextProcessingResultRepository processingResultRepository,
											   LabelingJobsRepository labelingJobsRepository,
											   IUserJobTasksRightsService userJobTasksRightsService,
											   IRawTextPreprocessorForNer textPreprocessorForNer,
											   INerAnnotatedTextParsingService nerAnnotatedTextParsingService) {
		this.processingResultRepository = processingResultRepository;
		this.labelingJobsRepository = labelingJobsRepository;
		this.userJobTasksRightsService = userJobTasksRightsService;
		this.textPreprocessorForNer = textPreprocessorForNer;
		this.nerAnnotatedTextParsingService = nerAnnotatedTextParsingService;
	}

	@Override
	public PageDto<NerAnnotationTextDto> getNextUnprocessed(final Integer jobId,
															final User user) throws NerServiceException {
		try {
			final Optional<LabelingJob> labelingJobOpt = this.labelingJobsRepository.findById(Long.valueOf(jobId));

			if (!labelingJobOpt.isPresent()) {
				return PageDto.empty();
			}

			final LabelingJob job = labelingJobOpt.get();
			final boolean canUserAccessTheJob = this.userJobTasksRightsService.doesUserHaveAccessToJobTasks(user, job);

			if (!canUserAccessTheJob) {
				return PageDto.empty();
			}

			final Page<NerJobTextItem> unprocessedTextItems = processingResultRepository.getUnprocessed(user, job,
					PageRequest.of(1, 20, Sort.by(Sort.Direction.DESC, "created")));

			final List<NerAnnotationTextDto> annTextDtos = Try.sequence(unprocessedTextItems.map(nerText -> Try.of(() -> {
				final List<TaggedTermDto> taggedTermDtos = this.textPreprocessorForNer.process(nerText.getText());
				return NerAnnotationTextDto.builder()
						.id(nerText.getId().intValue())
						.tokens(taggedTermDtos)
						.build();
			})).getContent()).getOrElseThrow(NerServiceException::new)
					.toList().asJava();

			return new PageDto<>(1, unprocessedTextItems.getNumberOfElements(), 20, annTextDtos);
		} catch (Exception e) {
			throw new NerServiceException(e);
		}
	}

	@Override
	public PageDto<NerAnnotationTextDto> getProcessed(final Integer jobId,
													  final User user,
													  final Integer page) throws NerServiceException {
		try {
			final Optional<LabelingJob> labelingJobOpt = this.labelingJobsRepository.findById(Long.valueOf(jobId));

			if (!labelingJobOpt.isPresent()) {
				return PageDto.empty();
			}

			final LabelingJob job = labelingJobOpt.get();
			final boolean canUserAccessTheJob = this.userJobTasksRightsService.doesUserHaveAccessToJobTasks(user, job);

			if (!canUserAccessTheJob) {
				return PageDto.empty();
			}

			final Page<UserNerTextProcessingResult> processedTexts = processingResultRepository.getForUserAndJob(user, job,
					PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC, "created")));

			final List<NerAnnotationTextDto> annotationTextDtos = new ArrayList<>();
			for (UserNerTextProcessingResult processedText : processedTexts) {
				annotationTextDtos.add(this.toDto(processedText));
			}


			return new PageDto<>(page, processedTexts.getSize(), 20, annotationTextDtos);
		} catch (Exception e) {
			throw new NerServiceException(e);
		}
	}


	private NerAnnotationTextDto toDto(UserNerTextProcessingResult userResult) throws NerServiceException {
		final NerJobTextItem textItem = userResult.getTextItem();
		return NerAnnotationTextDto.builder()
				.tokens(this.nerAnnotatedTextParsingService.parse(userResult.getAnnotatedText()))
				.text(textItem.getText())
				.id(textItem.getId().intValue())
				.build();
	}


}