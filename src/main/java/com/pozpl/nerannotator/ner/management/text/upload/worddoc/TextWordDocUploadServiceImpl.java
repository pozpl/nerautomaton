package com.pozpl.nerannotator.ner.management.text.upload.worddoc;

import com.pozpl.nerannotator.auth.dao.model.User;
import com.pozpl.nerannotator.ner.dao.model.job.LabelingJob;
import com.pozpl.nerannotator.ner.dao.model.text.NerJobTextItem;
import com.pozpl.nerannotator.ner.dao.repo.job.LabelingJobsRepository;
import com.pozpl.nerannotator.ner.dao.repo.text.NerJobTextItemRepository;
import com.pozpl.nerannotator.ner.management.text.upload.NerTextUploadResultDto;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import io.vavr.control.Try;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class TextWordDocUploadServiceImpl implements ITextWordDocUploadService {

    private static final Logger logger = LoggerFactory.getLogger(TextWordDocUploadServiceImpl.class);

    private final LabelingJobsRepository labelingJobsRepository;
    private final NerJobTextItemRepository nerJobTextItemRepository;

    @Autowired
    public TextWordDocUploadServiceImpl(LabelingJobsRepository labelingJobsRepository,
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

        final Optional<LabelingJob> labelingJobOpt = this.labelingJobsRepository.findById(jobId.longValue());
        if (!labelingJobOpt.isPresent()) {
            throw new NerServiceException("Can not fin Labeling job for text item editing");
        }

        final LabelingJob labelingJob = labelingJobOpt.get();

        if (! labelingJob.getOwner().getId().equals(user.getId())) {
            throw new NerServiceException(String.format("Labeling job Text save: attempt to save text item for job belonging to user %d " +
                    "from user %d session ", labelingJob.getOwner().getId(), user.getId()));
        }

        final Try<List<String>> textsTry = this.extractTextChunksFromDocument(file, labelingJob, user);
        boolean precessingStatus = textsTry.flatMapTry(texts -> this.processTexts(texts, labelingJob, user))
                .getOrElseThrow(NerServiceException::new);

        return NerTextUploadResultDto.ok();
    }


    private Try<Boolean> processTexts(final List<String> texts,
                                 final LabelingJob labelingJob,
                                 final User user) {
        try {
            for (String text : texts) {
                final String textMd5Hash = DigestUtils.md5DigestAsHex(text.getBytes());

                final Optional<NerJobTextItem> existingItem = nerJobTextItemRepository.getForJobAndHash(labelingJob,
                        textMd5Hash);
                if (!existingItem.isPresent()) {
                    final NerJobTextItem textItem = NerJobTextItem.of(labelingJob, text, textMd5Hash);

                    nerJobTextItemRepository.save(textItem);
                }
            }

            return Try.success(true);
        }catch (Exception e){
            logger.error("Error can not save text from the DOC file from the User: " + user.getId() + " for Labeling Job "
                    + labelingJob.getId());
            return Try.failure(e);
        }
    }

    private Try<List<String>> extractTextChunksFromDocument(final MultipartFile file,
                                                            final LabelingJob labelingJob,
                                                            final User user){
        try {
            final InputStream fis = file.getInputStream();
            final XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));

            final List<XWPFParagraph> paragraphList = xdoc.getParagraphs();
            final List<String> texts = new ArrayList<>();
            for (XWPFParagraph paragraph : paragraphList) {

                final String text = paragraph.getText();
                texts.add(text);
            }

            return Try.success(texts);
        } catch (Exception e) {
            logger.error("Error parsing DOC file from User: " + user.getId() + " for Labeling Job " + labelingJob.getId());
            return Try.failure(e);
        }
    }
}
