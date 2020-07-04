package com.pozpl.nerannotator.ner.management.text.csvupload;

import com.pozpl.nerannotator.auth.dao.model.User;
import com.pozpl.nerannotator.ner.dao.model.job.LabelingJob;
import com.pozpl.nerannotator.ner.dao.model.text.NerJobTextItem;
import com.pozpl.nerannotator.ner.dao.repo.job.LabelingJobsRepository;
import com.pozpl.nerannotator.ner.dao.repo.text.NerJobTextItemRepository;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Optional;

@Service
@Transactional
public class TextCsvUploadServiceImpl implements ITextCsvUploadService {

	private final LabelingJobsRepository labelingJobsRepository;
	private final NerJobTextItemRepository nerJobTextItemRepository;

	@Autowired
	public TextCsvUploadServiceImpl(LabelingJobsRepository labelingJobsRepository,
									NerJobTextItemRepository nerJobTextItemRepository) {
		this.labelingJobsRepository = labelingJobsRepository;
		this.nerJobTextItemRepository = nerJobTextItemRepository;
	}

	/**
	 * Process CSV file and add text from it to the presented Job
	 *
	 * @param file
	 * @param jobId
	 * @param user
	 * @return
	 * @throws NerServiceException
	 */
	@Override
	public NerTextCsvUploadResultDto processCsv(final MultipartFile file,
												final Integer jobId,
												final User user) throws NerServiceException {

		if(file.isEmpty()){
			return NerTextCsvUploadResultDto.error("File is empty");
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

		try (final Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
			 final CSVParser csvParser = new CSVParser(reader,CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				final String text = csvRecord.get("text");

				final String textMd5Hash = DigestUtils.md5DigestAsHex(text.getBytes());

				final Optional<NerJobTextItem> existingItem = nerJobTextItemRepository.getForJobAndHash(labelingJob,
						textMd5Hash);
				if(existingItem.isPresent()){
					final NerJobTextItem textItem = NerJobTextItem.of(labelingJob, text, textMd5Hash);

					nerJobTextItemRepository.save(textItem);
				}
			}

		} catch (IOException e) {
			throw new NerServiceException(e);
		}


		return NerTextCsvUploadResultDto.ok();
	}
}
