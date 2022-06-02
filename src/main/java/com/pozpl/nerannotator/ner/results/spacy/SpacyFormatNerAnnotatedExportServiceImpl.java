package com.pozpl.nerannotator.ner.results.spacy;

import com.fasterxml.jackson.databind.ObjectMapper;
import auth.dao.model.User;
import com.pozpl.nerannotator.ner.annotation.textprocess.BLIUOScheme;
import com.pozpl.nerannotator.ner.annotation.textprocess.INerAnnotatedTextParsingService;
import com.pozpl.nerannotator.ner.annotation.textprocess.TaggedTermDto;
import com.pozpl.nerannotator.ner.dao.model.job.LabelingJob;
import com.pozpl.nerannotator.ner.dao.model.text.UserNerTextProcessingResult;
import com.pozpl.nerannotator.ner.dao.repo.job.LabelingJobsRepository;
import com.pozpl.nerannotator.ner.dao.repo.text.UserTextProcessingResultRepository;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SpacyFormatNerAnnotatedExportServiceImpl implements ISpacyFormatNerAnnotatedExportService {

    private static final int MAX_ALLOWED_PAGES = 10000;

    private final UserTextProcessingResultRepository processingResultRepository;
    private final LabelingJobsRepository labelingJobsRepository;
    private final INerAnnotatedTextParsingService annotatedTextParsingService;

    private final ObjectMapper objectMapper;

    @Autowired
    public SpacyFormatNerAnnotatedExportServiceImpl(UserTextProcessingResultRepository processingResultRepository,
                                                    LabelingJobsRepository labelingJobsRepository,
                                                    INerAnnotatedTextParsingService annotatedTextParsingService) {
        this.processingResultRepository = processingResultRepository;
        this.labelingJobsRepository = labelingJobsRepository;
        this.annotatedTextParsingService = annotatedTextParsingService;

        this.objectMapper = new ObjectMapper();
    }


    @Override
    public void writeOwnerResultsToStream(final Integer jobId,
                                          final User owner,
                                          final OutputStream outputStream) throws NerServiceException {
        final Optional<LabelingJob> jobOpt = this.labelingJobsRepository.findByIdAndOwner(Long.valueOf(jobId), owner);
        if(! jobOpt.isPresent()){
            return;
        }

        final LabelingJob job = jobOpt.get();

        int page = 0;

        try(Writer streamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {

            Page<UserNerTextProcessingResult> processedTextsPage = processingResultRepository.getForUserAndJob(owner, job,
                    PageRequest.of(page, 100, Sort.by(Sort.Direction.ASC, "id")));

            final List<SpacySimpleTrainingNerFormat> documents = new ArrayList<>();
            while (!processedTextsPage.isEmpty()) {
                for (UserNerTextProcessingResult textResult : processedTextsPage.getContent()) {
                    final String annotatedText = textResult.getAnnotatedText();
                    final SpacySimpleTrainingNerFormat reformattedDocument = this.reformatBLIOUDocument(textResult.getAnnotatedText());
                    documents.add(reformattedDocument);
                }
                page++;
                if(page > MAX_ALLOWED_PAGES){//it's seems we are going to download too much data
                    break;
                }
                processedTextsPage = processingResultRepository.getForUserAndJob(owner, job,
                        PageRequest.of(page, 100, Sort.by(Sort.Direction.ASC, "id")));
            }

            final String annotatedTextsJson = objectMapper.writeValueAsString(documents);

            streamWriter.write(annotatedTextsJson);

        } catch (IOException e) {
            throw new NerServiceException(e);
        }
    }


    private SpacySimpleTrainingNerFormat reformatBLIOUDocument(final String annotatedText) throws NerServiceException {
        final List<TaggedTermDto> taggedTerms = this.annotatedTextParsingService.parse(annotatedText);

        final StringBuilder textBuilder = new StringBuilder();
        List<SpacySimpleTrainingNerFormat.Entity> entities = new ArrayList<>();

        int begin = 0;
        for (final TaggedTermDto taggedTerm : taggedTerms) {

            if(textBuilder.length() != 0 && ! (StringUtils.equalsAny(taggedTerm.getToken(), ".", ",")
                 || StringUtils.startsWith(taggedTerm.getToken(), "'"))
            ) {
                textBuilder.append(" ");
            }

            if (taggedTerm.getLabel() != null){
                if (BLIUOScheme.BEGIN.equals(taggedTerm.getPosition())){
                    begin = textBuilder.length();
                }
            }

            textBuilder.append(taggedTerm.getToken());


            if (taggedTerm.getLabel() != null){
                if (BLIUOScheme.LAST.equals(taggedTerm.getPosition())){
                    entities.add(new SpacySimpleTrainingNerFormat.Entity(
                            begin,
                            textBuilder.length(),
                            taggedTerm.getLabel()
                    ));
                }else if (BLIUOScheme.UNIT.equals(taggedTerm.getPosition())){
                    entities.add(new SpacySimpleTrainingNerFormat.Entity(
                            textBuilder.length() - taggedTerm.getToken().length(),
                            textBuilder.length(),
                            taggedTerm.getLabel()
                    ));
                }
            }
        }

        return new SpacySimpleTrainingNerFormat(
                textBuilder.toString(),
                entities
        );

    }

}
