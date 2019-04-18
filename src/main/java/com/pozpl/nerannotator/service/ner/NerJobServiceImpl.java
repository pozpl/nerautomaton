package com.pozpl.nerannotator.service.ner;

import com.pozpl.nerannotator.persistence.dao.job.LabelingTaskRepository;
import com.pozpl.nerannotator.persistence.model.LanguageCodes;
import com.pozpl.nerannotator.persistence.model.User;
import com.pozpl.nerannotator.persistence.model.job.LabelingJob;
import com.pozpl.nerannotator.service.exceptions.NerServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Optional;

@Service
@Transactional
public class NerJobServiceImpl implements INerJobService {

	private final LabelingTaskRepository labelingTaskRepository;

	@Autowired
	public NerJobServiceImpl(final LabelingTaskRepository labelingTaskRepository) {
		this.labelingTaskRepository = labelingTaskRepository;
	}

	/**
	 * Get Job by Id
	 *
	 * @param id
	 * @param user
	 * @return
	 * @throws NerServiceException
	 */
	@Override
	public Optional<NerJobDto> getJobById(Integer id, User user) throws NerServiceException {
		try {
			final Optional<LabelingJob> nerJobOpt = labelingTaskRepository.findByIdAndOwner(new Long(id), user);

			return nerJobOpt.map(nerJob -> toDto(nerJob));

		} catch (Exception e) {
			throw new NerServiceException(e);
		}
	}

	/**
	 * Get list of jobs
	 *
	 * @param owner
	 * @param page
	 * @return
	 * @throws NerServiceException
	 */
	@Override
	public Page<NerJobDto> getJobsForOwner(final User owner,final Integer page) throws NerServiceException {
		try {
			final Integer adjustedPage = page != null && page > 0 ? page -1 : 0;
			Page<LabelingJob> userJobs = labelingTaskRepository.getJobsForOwner(owner, PageRequest.of(adjustedPage, 20, Sort.by(Sort.Direction.DESC, "created")));

			return userJobs.map(nerJob -> toDto(nerJob));
		} catch (Exception e) {
			throw new NerServiceException(e);
		}
	}

	/**
	 * Save Job
	 *
	 * @param nerJobDto
	 * @param user
	 * @return
	 * @throws NerServiceException
	 */
	@Override
	public NerJobSaveStatusDto saveJob(final NerJobDto nerJobDto, final User user) throws NerServiceException {
		try {
			final LabelingJob labelingJobOriginal;

			if (nerJobDto.getId() != null) { //Update case
				final Optional<LabelingJob> nerJobOpt = labelingTaskRepository.findByIdAndOwner(new Long(nerJobDto.getId()), user);

				if (nerJobOpt.isPresent()) {
					labelingJobOriginal = nerJobOpt.get();
				} else {
					labelingJobOriginal = LabelingJob.builder()
							.owner(user)
							.created(Calendar.getInstance())
							.languageCode(LanguageCodes.EN)
							.build();
				}

			} else {
				labelingJobOriginal = LabelingJob.builder()
						.owner(user)
						.created(Calendar.getInstance())
						.languageCode(LanguageCodes.EN)
						.build();
			}

			final LabelingJob labelingJobToSave = labelingJobOriginal.toBuilder()
					.name(nerJobDto.getName())
					.updated(Calendar.getInstance())
					.languageCode(LanguageCodes.EN)//Only support EN by default
					.build();

			labelingTaskRepository.save(labelingJobToSave);

			final NerJobSaveStatusDto nerJobSaveStatusDto = NerJobSaveStatusDto.builder()
					.status(NerJobSaveStatusDto.SaveStatus.OK)
					.nerJobDto(toDto(labelingJobToSave))
					.build();

			return nerJobSaveStatusDto;
		} catch (Exception e) {
			throw new NerServiceException(e);
		}
	}

	/**
	 * Delete job by id
	 *
	 * @param user
	 * @param jobId
	 * @throws NerServiceException
	 */
	@Override
	public void deleteJob(User user, Integer jobId) throws NerServiceException {
		try {
			final Optional<LabelingJob> nerJobOpt = labelingTaskRepository.findByIdAndOwner(new Long(jobId), user);
			if (nerJobOpt.isPresent()) {
				labelingTaskRepository.delete(nerJobOpt.get());
			}
		} catch (Exception e) {
			throw new NerServiceException(e);
		}

	}


	private NerJobDto toDto(final LabelingJob labelingJob) {
		return NerJobDto.builder()
				.id(labelingJob.getId().intValue())
				.name(labelingJob.getName())
				.created(labelingJob.getCreated().getTime())
				.build();
	}
}
