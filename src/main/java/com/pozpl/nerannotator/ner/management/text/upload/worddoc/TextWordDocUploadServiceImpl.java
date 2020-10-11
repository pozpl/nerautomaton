package com.pozpl.nerannotator.ner.management.text.upload.worddoc;

import com.pozpl.nerannotator.auth.dao.model.User;
import com.pozpl.nerannotator.ner.dao.model.job.LabelingJob;
import com.pozpl.nerannotator.ner.dao.model.text.NerJobTextItem;
import com.pozpl.nerannotator.ner.dao.repo.job.LabelingJobsRepository;
import com.pozpl.nerannotator.ner.dao.repo.text.NerJobTextItemRepository;
import com.pozpl.nerannotator.ner.management.text.upload.NerTextUploadResultDto;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import io.vavr.control.Try;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.io.StringReader;
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

        final Try<List<String>> textsTry = getExamplesFromPage(file, labelingJob, user);
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


    private Try<List<String>> getExamplesFromPage(final MultipartFile file,
                                                  final LabelingJob labelingJob,
                                                  final User user){
        try {
            final InputStream fis = file.getInputStream();
            final XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
            final List<String> texts = new ArrayList<>();

            //Determine how many paragraphs per page
            final List<Integer> paragraphCountList = getParagraphCountPerPage(xdoc);

            if (paragraphCountList != null && paragraphCountList.size() > 0) {

                int docCount = 0;
                int startIndex = 0;
                int endIndex = paragraphCountList.get(0);


                //Loop through the paragraph counts for each page
                for (int i=0; i < paragraphCountList.size(); i++) {
                    final List<XWPFParagraph> paragraphs = xdoc.getParagraphs();

                    if (paragraphs != null && paragraphs.size() > 0) {

                        final StringBuilder pageStringBuilder = new StringBuilder();

                        //Get the paragraphs from the input Word document
                        for (int j=startIndex; j < endIndex; j++) {
                            if (paragraphs.get(j) != null) {
                                final XWPFParagraph paragraph = paragraphs.get(j);
                                final String text  = paragraph.getText();
                                pageStringBuilder.append(text);
                                pageStringBuilder.append("\n");
                            }
                        }

                        //Set the start and end point for the next set of paragraphs
                        startIndex = endIndex;

                        if (i < paragraphCountList.size()-2) {
                            endIndex = endIndex + paragraphCountList.get(i+1);
                        } else {
                            endIndex = paragraphs.size()-1;
                        }

                        final String pageText = pageStringBuilder.toString();
                        if(! StringUtils.isBlank(pageText)) {
                            texts.add(pageText);
                        }

                    }
                }
            }

            return Try.success(texts);
        } catch (Exception e) {
            logger.error("Error parsing DOC file from User: " + user.getId() + " for Labeling Job " + labelingJob.getId());
            return Try.failure(e);
        }
    }

    private List<Integer> getParagraphCountPerPage(final XWPFDocument doc) throws Exception {
        final List<Integer> paragraphCountList = new ArrayList<>();
        int paragraphCount = 0;

        final Document domDoc = convertStringToDOM(doc.getDocument().getBody().toString());
        final NodeList rootChildNodeList = domDoc.getChildNodes().item(0).getChildNodes();

        for (int i=0; i < rootChildNodeList.getLength(); i++) {
            final Node childNode = rootChildNodeList.item(i);

            if (childNode.getNodeName().equals("w:p")) {
                paragraphCount++;

                if (childNode.getChildNodes() != null) {
                    for (int k=0; k < childNode.getChildNodes().getLength(); k++) {
                        if (childNode.getChildNodes().item(k).getNodeName().equals("w:r")) {
                            for (int m=0; m < childNode.getChildNodes().item(k).getChildNodes().getLength(); m++) {
                                if (childNode.getChildNodes().item(k).getChildNodes().item(m).getNodeName().equals("w:br")) {

                                    paragraphCountList.add(paragraphCount);
                                    paragraphCount = 0;
                                }
                            }
                        }
                    }
                }
            }
        }
        paragraphCountList.add(paragraphCount+1);

        return paragraphCountList;
    }


    private Document convertStringToDOM(String xmlData) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document =  builder.parse(new InputSource(new StringReader(xmlData)));

        return document;
    }
}
