package com.pozpl.nerannotator.ner.impl.annotation.edit;

import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.nerannotator.ner.impl.annotation.rights.IUserJobTasksRightsService;
import com.pozpl.nerannotator.ner.impl.annotation.textprocess.INerAnnotatedTextParsingService;
import com.pozpl.nerannotator.ner.impl.annotation.textprocess.IRawTextPreprocessorForNer;
import com.pozpl.nerannotator.ner.impl.annotation.textprocess.TaggedTermDto;
import com.pozpl.nerannotator.ner.impl.dao.model.job.LabelingJob;
import com.pozpl.nerannotator.ner.impl.dao.model.text.NerJobTextItem;
import com.pozpl.nerannotator.ner.impl.dao.model.text.UserNerTextProcessingResult;
import com.pozpl.nerannotator.ner.impl.dao.model.user.UserId;
import com.pozpl.nerannotator.ner.impl.dao.repo.job.LabelingJobsRepository;
import com.pozpl.nerannotator.ner.impl.dao.repo.text.UserTextProcessingResultRepository;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.shared.pagination.PageDto;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	public List<NerTextAnnotationDto> getNextUnprocessed(final Integer jobId,
														 final UserIntDto user) throws NerServiceException {
		try {
			final Optional<LabelingJob> labelingJobOpt = this.labelingJobsRepository.findById(Long.valueOf(jobId));

			if (!labelingJobOpt.isPresent()) {
				return Collections.emptyList();
			}

			final LabelingJob job = labelingJobOpt.get();
			final boolean canUserAccessTheJob = this.userJobTasksRightsService.doesUserHaveAccessToJobTasks(user, job);

			if (!canUserAccessTheJob) {
				return Collections.emptyList();
			}

			final List<NerJobTextItem> unprocessedTextItems = processingResultRepository.getUnprocessed(UserId.of(user), job,
					PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "created")));

			final List<NerTextAnnotationDto> annTextDtos = Try.sequence(unprocessedTextItems.stream()
					.map(nerText -> Try.of(() -> {
						final List<TaggedTermDto> taggedTermDtos = this.textPreprocessorForNer.process(nerText.getText());
						return NerTextAnnotationDto.builder()
								.id(nerText.getId().intValue())
								.text(nerText.getText())
								.tokens(taggedTermDtos)
								.build();
					}))
					.collect(Collectors.toList()))
					.getOrElseThrow(NerServiceException::new)
					.toList().asJava();

			return annTextDtos;
		} catch (Exception e) {
			throw new NerServiceException(e);
		}
	}

	@Override
	public PageDto<NerTextAnnotationDto> getProcessed(final Integer jobId,
													  final UserIntDto user,
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

			final Page<UserNerTextProcessingResult> processedTexts = processingResultRepository.getForUserAndJob(UserId.of(user), job,
					PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC, "created")));

			final List<NerTextAnnotationDto> annotationTextDtos = Try.sequence(processedTexts.get()
					.map(processeText -> Try.of(() -> this.toDto(processeText)))
					.collect(Collectors.toList())).getOrElseThrow(NerServiceException::new)
					.toJavaList();

			return new PageDto<>(page, Long.valueOf(processedTexts.getTotalElements()).intValue(), 20, annotationTextDtos);
		} catch (Exception e) {
			throw new NerServiceException(e);
		}
	}


	private NerTextAnnotationDto toDto(UserNerTextProcessingResult userResult) throws NerServiceException {
		final NerJobTextItem textItem = userResult.getTextItem();
		return NerTextAnnotationDto.builder()
				.tokens(this.nerAnnotatedTextParsingService.parse(userResult.getAnnotatedText()))
				.text(textItem.getText())
				.id(textItem.getId().intValue())
				.build();
	}


}
