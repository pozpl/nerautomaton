package com.pozpl.nerannotator.ner.management.job;

import com.pozpl.nerannotator.ner.dao.repo.job.LabelingJobsRepository;
import com.pozpl.nerannotator.ner.dao.model.LanguageCodes;
import com.pozpl.nerannotator.auth.dao.model.User;
import com.pozpl.nerannotator.ner.dao.model.job.LabelingJob;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.ner.management.labels.INerLabelEditingService;
import com.pozpl.nerannotator.ner.management.labels.NerLabelDto;
import com.pozpl.nerannotator.shared.pagination.PageDto;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class NerJobServiceImpl implements INerJobService {

	private final LabelingJobsRepository labelingJobsRepository;

	private INerLabelEditingService nerLabelEditingService;

	@Autowired
	public NerJobServiceImpl(final LabelingJobsRepository labelingJobsRepository,
							 final INerLabelEditingService nerLabelEditingService) {
		this.labelingJobsRepository = labelingJobsRepository;
		this.nerLabelEditingService = nerLabelEditingService;
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
			final Optional<LabelingJob> nerJobOpt = labelingJobsRepository.findByIdAndOwner(Long.valueOf(id), user);

			return nerJobOpt.map(nerJob -> Try.of(() -> toDto(nerJob)))
					.flatMap(tryDto -> {
						if(tryDto.isFailure()){
							return Optional.empty();
						}else{
							return Optional.ofNullable(tryDto.get());
						}
					});

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
	public PageDto<NerJobDto> getJobsForOwner(final User owner, final Integer page) throws NerServiceException {
		try {
			final Integer adjustedPage = page != null && page > 0 ? page -1 : 0;
			Page<LabelingJob> userJobs = labelingJobsRepository.getJobsForOwner(owner, PageRequest.of(adjustedPage, 20, Sort.by(Sort.Direction.DESC, "created")));

			final List<NerJobDto> jobs = userJobs.getContent().stream()
					.map(nerJob -> Try.of(() -> toDto(nerJob)))
					.map(nerJobTry -> nerJobTry.getOrElse(new NerJobDto()))
					.collect(Collectors.toList());

			return new PageDto<>(page, userJobs.getSize(), 20, jobs);
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
				final Optional<LabelingJob> nerJobOpt = labelingJobsRepository.findByIdAndOwner(Long.valueOf(nerJobDto.getId()), user);

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

			labelingJobsRepository.save(labelingJobToSave);

			this.nerLabelEditingService.saveAllLabelsForJob(labelingJobToSave, nerJobDto.getLabels());


			return  NerJobSaveStatusDto.builder()
					.status(NerJobSaveStatusDto.SaveStatus.OK)
					.nerJobDto(toDto(labelingJobToSave))
					.build();
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
			final Optional<LabelingJob> nerJobOpt = labelingJobsRepository.findByIdAndOwner(Long.valueOf(jobId), user);
			if (nerJobOpt.isPresent()) {
				labelingJobsRepository.delete(nerJobOpt.get());
			}
		} catch (Exception e) {
			throw new NerServiceException(e);
		}

	}


	private NerJobDto toDto(final LabelingJob labelingJob) throws NerServiceException {

		final List<NerLabelDto> nerLabelDtos = this.nerLabelEditingService.listLabelsAvailableForTask(labelingJob);

		return NerJobDto.builder()
				.id(labelingJob.getId().intValue())
				.name(labelingJob.getName())
				.created(labelingJob.getCreated().getTime())
				.labels(nerLabelDtos)
				.build();
	}
}
