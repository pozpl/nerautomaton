package com.pozpl.nerannotator.service.ner.text;

import com.pozpl.nerannotator.persistence.dao.job.LabelingJobsRepository;
import com.pozpl.nerannotator.persistence.dao.ner.NerJobTextItemRepository;
import com.pozpl.nerannotator.persistence.model.User;
import com.pozpl.nerannotator.persistence.model.job.LabelingJob;
import com.pozpl.nerannotator.persistence.model.ner.NerJobTextItem;
import com.pozpl.nerannotator.service.exceptions.NerServiceException;
import com.pozpl.nerannotator.service.registration.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Optional;

@Service
@Transactional
public class JobTextEditServiceImpl implements IJobTextEditService {

	private final NerJobTextItemRepository nerJobTextItemRepository;
	private final LabelingJobsRepository labelingJobsRepository;

	public JobTextEditServiceImpl(NerJobTextItemRepository nerJobTextItemRepository,
								  LabelingJobsRepository labelingJobsRepository) {
		this.nerJobTextItemRepository = nerJobTextItemRepository;
		this.labelingJobsRepository = labelingJobsRepository;
	}

	/**
	 * Get job by Id
	 *
	 * @param id
	 * @param user
	 * @return
	 * @throws NerServiceException
	 */
	@Override
	public JobTextDto getById(final Integer id,
							  final User user) throws NerServiceException {
		try {
			final Optional<NerJobTextItem> maybeText = this.nerJobTextItemRepository.findById(Long.valueOf(id));

			if (maybeText.isPresent()) {
				final NerJobTextItem textItem = maybeText.get();

				final LabelingJob labelingJob = textItem.getJob();//TODO change to DAO method call

				if (labelingJob.getOwner().getId() != user.getId()) {
					throw new NerServiceException(String.format("Labeling job Text save: attempt to save text item for job belonging to user %d " +
							"from user %d session ", labelingJob.getOwner().getId(), user.getId()));
				}

				return toDto(textItem, labelingJob);
			}

			return JobTextDto.builder().build();//return empty DTO
		} catch (Exception e) {
			throw new NerServiceException(e);
		}

	}

	/**
	 * Save job text information provided by dto
	 *
	 * @param jobTextDto
	 * @param user
	 * @return
	 * @throws NerServiceException
	 */
	@Override
	public JobTextEditStatusDto save(final JobTextDto jobTextDto,
									 final User user) throws NerServiceException {
		try {

			if (jobTextDto.getJobId() == null) {
				throw new NerServiceException("Attempt to save Ner Text without Job ID");
			}

			final Optional<LabelingJob> labelingJobOpt = this.labelingJobsRepository.findById(jobTextDto.getJobId().longValue());
			if (!labelingJobOpt.isPresent()) {
				throw new NerServiceException("Can not fin Labeling job for text item editing");
			}

			final LabelingJob labelingJob = labelingJobOpt.get();

			if (labelingJob.getOwner().getId() != user.getId()) {
				throw new NerServiceException(String.format("Labeling job Text save: attempt to save text item for job belonging to user %d " +
						"from user %d session ", labelingJob.getOwner().getId(), user.getId()));
			}

			if(StringUtils.isBlank(jobTextDto.getText())){
				return JobTextEditStatusDto
						.builder()
						.error(true)
						.errorCode(JobTextEditStatusDto.ErrorCode.EMPTY_TEXT)
						.build();
			}

			final NerJobTextItem.NerJobTextItemBuilder textItemBuilder;
			if (jobTextDto.getId() != null) {
				final Optional<NerJobTextItem> existingTextItemOpt = this.nerJobTextItemRepository.findById(Long.valueOf(jobTextDto.getId()));

				if (existingTextItemOpt.isPresent()) {
					final NerJobTextItem textItem = existingTextItemOpt.get();

					final LabelingJob labelingJobFromText = textItem.getJob();//TODO change to DAO method call

					if (labelingJob.getId() != labelingJobFromText.getId()) {
						throw new NerServiceException(String.format("Labeling job Text save: attempt to save text belonging to labeling job  %d " +
										"but with labeling job %d provided ", labelingJobFromText.getId(),
								labelingJob.getId()));
					}

					textItemBuilder = textItem.toBuilder();
				} else {
					throw new NerServiceException(String.format("Can not find Ner text for id %d for saving ", jobTextDto.getId()));
				}
			} else {
			 	textItemBuilder = NerJobTextItem.builder()
				.job(labelingJob)
				.created(Calendar.getInstance());
			}

			final NerJobTextItem textItem = textItemBuilder
					.text(jobTextDto.getText())
					.md5Hash(DigestUtils.md5DigestAsHex(jobTextDto.getText().getBytes()))
					.updated(Calendar.getInstance())
					.build();



			return JobTextEditStatusDto.builder()
					.jobTextDto(toDto(textItem, labelingJob))
					.error(false)
					.build();
		} catch (Exception e) {
			throw new NerServiceException(e);
		}
	}

	/**
	 * Delete job text
	 *
	 * @param jobTextId
	 * @param user
	 * @throws NerServiceException
	 */
	@Override
	public void deleteJobText(Integer jobTextId, User user) throws NerServiceException {

	}


	/**
	 * Get DTO fomr entity
	 *
	 * @param jobTextItem
	 * @param labelingJob
	 * @return
	 */
	private JobTextDto toDto(final NerJobTextItem jobTextItem,
							 final LabelingJob labelingJob) {
		return JobTextDto.builder()
				.jobId(labelingJob.getId().intValue())
				.text(jobTextItem.getText())
				.id(jobTextItem.getId().intValue())
				.build();
	}
}
