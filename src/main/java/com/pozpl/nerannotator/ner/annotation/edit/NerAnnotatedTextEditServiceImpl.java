package com.pozpl.nerannotator.ner.annotation.edit;

import com.pozpl.nerannotator.auth.dao.model.User;
import com.pozpl.nerannotator.ner.annotation.rights.IUserJobTasksRightsService;
import com.pozpl.nerannotator.ner.annotation.textprocess.INerAnnotatedTextParsingService;
import com.pozpl.nerannotator.ner.dao.model.job.LabelingJob;
import com.pozpl.nerannotator.ner.dao.model.text.NerJobTextItem;
import com.pozpl.nerannotator.ner.dao.model.text.NerLabel;
import com.pozpl.nerannotator.ner.dao.model.text.UserNerTextProcessingResult;
import com.pozpl.nerannotator.ner.dao.repo.text.NerJobTextItemRepository;
import com.pozpl.nerannotator.ner.dao.repo.text.NerLabelsRepository;
import com.pozpl.nerannotator.ner.dao.repo.text.UserTextProcessingResultRepository;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class NerAnnotatedTextEditServiceImpl implements INerAnnotatedTextEditService {

	private final UserTextProcessingResultRepository processingResultRepository;
	private final NerJobTextItemRepository nerJobTextItemRepository;
	private final IUserJobTasksRightsService userJobTasksRightsService;
	private final INerAnnotatedTextParsingService nerAnnotatedTextParsingService;
	private final NerLabelsRepository nerLabelsRepository;

	@Autowired
	public NerAnnotatedTextEditServiceImpl(UserTextProcessingResultRepository processingResultRepository,
										   NerJobTextItemRepository nerJobTextItemRepository,
										   IUserJobTasksRightsService userJobTasksRightsService,
										   INerAnnotatedTextParsingService nerAnnotatedTextParsingService,
										   NerLabelsRepository nerLabelsRepository) {
		this.processingResultRepository = processingResultRepository;
		this.nerJobTextItemRepository = nerJobTextItemRepository;
		this.userJobTasksRightsService = userJobTasksRightsService;
		this.nerAnnotatedTextParsingService = nerAnnotatedTextParsingService;
		this.nerLabelsRepository = nerLabelsRepository;
	}

	@Override
	public NerTextAnnotationEditResultDto save(final NerTextAnnotationDto annotationTextDto,
									 final User user) throws NerServiceException {

		final Optional<NerJobTextItem> textItemOpt = nerJobTextItemRepository.findById(Long.valueOf(annotationTextDto.getId()));

		if(! textItemOpt.isPresent()){
			return NerTextAnnotationEditResultDto.error("Access violation");
		}
		final NerJobTextItem textItem = textItemOpt.get();

		final LabelingJob job = nerJobTextItemRepository.getJobForItem(textItem);
		final boolean userHaveRights = userJobTasksRightsService.doesUserHaveAccessToJobTasks(user, job);

		if(! userHaveRights){
			return NerTextAnnotationEditResultDto.error("Access violation");
		}

		if(annotationTextDto.getTokens() == null){
			return NerTextAnnotationEditResultDto.error("empty result");
		}

		final List<NerLabel> availableLabels =  nerLabelsRepository.getForJob(job);
		final List<String> labels = availableLabels.stream().map(NerLabel::getName).collect(Collectors.toList());
		final String serialisedTokens = nerAnnotatedTextParsingService.serialise(annotationTextDto.getTokens(), labels);

		final Optional<UserNerTextProcessingResult> existingResultOpt = processingResultRepository.getForUserAndTextItem(user, textItem);
		final UserNerTextProcessingResult resultToSave;
		if(existingResultOpt.isPresent()){
			resultToSave = existingResultOpt.get();
			resultToSave.setAnnotatedText(serialisedTokens);
		}else{
			resultToSave = UserNerTextProcessingResult.builder()
					.annotatedText(serialisedTokens)
					.user(user)
					.textItem(textItem)
					.build();
		}

		processingResultRepository.save(resultToSave);


		return NerTextAnnotationEditResultDto.success(annotationTextDto);
	}
}
