package com.pozpl.nerannotator.ner.results;

import auth.dao.model.User;
import com.pozpl.nerannotator.ner.dao.model.job.LabelingJob;
import com.pozpl.nerannotator.ner.dao.model.text.UserNerTextProcessingResult;
import com.pozpl.nerannotator.ner.dao.repo.job.LabelingJobsRepository;
import com.pozpl.nerannotator.ner.dao.repo.text.UserTextProcessingResultRepository;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
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
import java.util.Optional;

@Service
@Transactional
public class ResultsBLIUOExportServiceImpl implements IResultsBLIUOExportService {

    private static final int MAX_ALLOWED_PAGES = 10000;

    private final UserTextProcessingResultRepository processingResultRepository;
    private final LabelingJobsRepository labelingJobsRepository;

    public ResultsBLIUOExportServiceImpl(UserTextProcessingResultRepository processingResultRepository,
                                         LabelingJobsRepository labelingJobsRepository) {
        this.processingResultRepository = processingResultRepository;
        this.labelingJobsRepository = labelingJobsRepository;
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
            while (!processedTextsPage.isEmpty()) {
                for (UserNerTextProcessingResult textResult : processedTextsPage.getContent()) {
                    final String annotatedText = textResult.getAnnotatedText();
                    streamWriter.write(annotatedText);
                }
                page++;
                if(page > MAX_ALLOWED_PAGES){//it's seems we are going to download too much data
                    break;
                }
                processedTextsPage = processingResultRepository.getForUserAndJob(owner, job,
                        PageRequest.of(page, 100, Sort.by(Sort.Direction.ASC, "id")));
            }
        } catch (IOException e) {
            throw new NerServiceException(e);
        }

    }
}
