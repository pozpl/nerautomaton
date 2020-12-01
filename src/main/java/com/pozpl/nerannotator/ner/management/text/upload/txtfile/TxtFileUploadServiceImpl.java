package com.pozpl.nerannotator.ner.management.text.upload.txtfile;

import com.pozpl.nerannotator.auth.dao.model.User;
import com.pozpl.nerannotator.ner.dao.model.job.LabelingJob;
import com.pozpl.nerannotator.ner.dao.model.text.NerJobTextItem;
import com.pozpl.nerannotator.ner.dao.repo.job.LabelingJobsRepository;
import com.pozpl.nerannotator.ner.dao.repo.text.NerJobTextItemRepository;
import com.pozpl.nerannotator.ner.management.text.upload.NerTextUploadResultDto;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

@Service
@Transactional
public class TxtFileUploadServiceImpl implements ITxtFileUploadService {

    private static final Logger logger = LoggerFactory.getLogger(TxtFileUploadServiceImpl.class);

    private final LabelingJobsRepository labelingJobsRepository;
    private final NerJobTextItemRepository nerJobTextItemRepository;

    @Autowired
    public TxtFileUploadServiceImpl(LabelingJobsRepository labelingJobsRepository,
                                    NerJobTextItemRepository nerJobTextItemRepository) {
        this.labelingJobsRepository = labelingJobsRepository;
        this.nerJobTextItemRepository = nerJobTextItemRepository;
    }

    @Override
    public NerTextUploadResultDto processFile(final MultipartFile file,
                                              final Integer jobId,
                                              final User user) throws NerServiceException {

        if(file.isEmpty()){
            return NerTextUploadResultDto.error("File is empty");
        }
        if(file.getSize() > 1038336){
            return NerTextUploadResultDto.error("File too large");
        }
        final String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        if( ! (StringUtils.isNotBlank(fileExtension) && StringUtils.equalsAnyIgnoreCase(fileExtension, "txt"))){
            return NerTextUploadResultDto.error("File is not a text file");
        }

        final Optional<LabelingJob> labelingJobOpt = this.labelingJobsRepository.findById(jobId.longValue());
        if (!labelingJobOpt.isPresent()) {
            throw new NerServiceException("Can not fin Labeling job for text item editing");
        }

        final LabelingJob labelingJob = labelingJobOpt.get();

        if (! labelingJob.getOwner().getId().equals(user.getId())) {
            throw new NerServiceException(String.format("Labeling job Text save: attempt to save text item for job belonging to user %d " +
                    "from user %d session ", labelingJob.getOwner().getId(), user.getId()));
        }


        try(final InputStream fileStream = file.getInputStream()){
            final BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream, "UTF-8"));
            final StringBuilder content = new StringBuilder();
            String line;
            int newLineCounter = 0;
            while ((line = reader.readLine()) != null) {
                if(StringUtils.isEmpty(line)){
                    newLineCounter++;
                }
                if(newLineCounter >= 3){
                    newLineCounter = 0;
                    final String textToSave = content.toString();
                    final boolean result  = processText(textToSave, labelingJob, user);
                    content.setLength(0);//clearing the buffer
                }else {
                    content.append(line + "\n");
                    if(newLineCounter >=1 && StringUtils.isNotEmpty(line)){
                        newLineCounter = 0;//if next line after the new line is not empty reset counter
                    }
                }
            }

            final String textToSave = content.toString();
            if(StringUtils.isNotEmpty(textToSave)) {
                final boolean result = processText(textToSave, labelingJob, user);
            }

        } catch (IOException e) {
            throw new NerServiceException(e);
        }

        return NerTextUploadResultDto.ok();
    }


    private boolean processText(final String text,
                                      final LabelingJob labelingJob,
                                      final User user) throws NerServiceException {
        try {
            final String trimmedText = text.trim();

            final String textMd5Hash = DigestUtils.md5DigestAsHex(trimmedText.getBytes());

            final Optional<NerJobTextItem> existingItem = nerJobTextItemRepository.getForJobAndHash(labelingJob,
                    textMd5Hash);
            if (!existingItem.isPresent()) {
                final NerJobTextItem textItem = NerJobTextItem.of(labelingJob, trimmedText, textMd5Hash);

                nerJobTextItemRepository.save(textItem);
            }

            return true;
        }catch (Exception e){
            logger.error("Error can not save text from the TXT file from the User: " + user.getId() + " for Labeling Job "
                    + labelingJob.getId());
            throw new NerServiceException(e);
        }
    }
}
