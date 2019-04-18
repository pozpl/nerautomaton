package com.pozpl.nerannotator.service.ner.labels;

import com.pozpl.nerannotator.persistence.dao.job.LabelingTaskRepository;
import com.pozpl.nerannotator.persistence.dao.ner.NerLabelsRepository;
import com.pozpl.nerannotator.persistence.model.job.LabelingJob;
import com.pozpl.nerannotator.persistence.model.ner.NerLabel;
import com.pozpl.nerannotator.service.exceptions.NerServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class NerLabelEditingServiceImpl implements INerLabelEditingService {

	private LabelingTaskRepository labelingTaskRepository;

	private NerLabelsRepository nerLabelsRepository;

	@Autowired
	public NerLabelEditingServiceImpl(LabelingTaskRepository labelingTaskRepository,
									  NerLabelsRepository nerLabelsRepository) {
		this.labelingTaskRepository = labelingTaskRepository;
		this.nerLabelsRepository = nerLabelsRepository;
	}

	/**
	 * Get labels for job
	 *
	 * @param labelingJob
	 * @return
	 */
	@Override
	public List<NerLabelDto> listLabelsAvailableForTask(LabelingJob labelingJob) throws NerServiceException {
		try {
			final List<NerLabel> availableEntities = this.nerLabelsRepository.getForJob(labelingJob);

			return  availableEntities.stream().map(entityLabel -> toDto(entityLabel)).collect(Collectors.toList());

		}catch (Exception e){
			throw new NerServiceException(e);
		}
	}

	/**
	 * Get labeling Dto by Id and ensure that it belongs to task
	 *
	 * @param id
	 * @param labelingJob
	 * @return
	 */
	@Override
	public Optional<NerLabelDto> getById(Long id, LabelingJob labelingJob) throws NerServiceException {

		try {
			final Optional<NerLabel> nerJobAvailableEntityOptional = this.nerLabelsRepository.findById(id);

			return nerJobAvailableEntityOptional.flatMap(entityLabel -> {
				if(! entityLabel.getJob().equals(labelingJob)){
					return Optional.empty();
				}
				return Optional.ofNullable(toDto(entityLabel));
			});
		}catch (Exception e){
			throw new NerServiceException(e);
		}
	}

	/**
	 * Save Job
	 *
	 * @param nerLabelDto
	 * @param labelingJob
	 * @return
	 * @throws NerServiceException
	 */
	@Override
	public NerLabelEditStatusDto saveLabel(NerLabelDto nerLabelDto, LabelingJob labelingJob) throws NerServiceException {
		try {

			final NerLabel.NerLabelBuilder labelBuilder;

			final Optional<NerLabel> labelFoundByNameOpt =  this.nerLabelsRepository.getByNameAndJob(nerLabelDto.getName(),
					labelingJob);

			if(nerLabelDto.getId() != null){

				final Optional<NerLabel> labelOpt = this.nerLabelsRepository.findById(nerLabelDto.getId().longValue());

				if (! labelOpt.isPresent()){
					throw new NerServiceException(String.format("Can not find ner label by id=%d that belongs " +
							"to task %d (%s) ", nerLabelDto.getId(), labelingJob.getId(), labelingJob.getName()));
				}

				final NerLabel label = labelOpt.get();
				if(! label.getJob().equals(labelingJob)){
					throw new NerServiceException(String.format("Attempt to edit Ner label id=%d that belongs " +
							"to task %d (%s)  from task %d (%s) session", nerLabelDto.getId(), label.getJob().getId(),
							label.getJob().getName(), labelingJob.getId(), labelingJob.getName()));
				}




				if (labelFoundByNameOpt.isPresent() &&
				! labelFoundByNameOpt.get().getId().equals(label.getId())){
					return NerLabelEditStatusDto.builder().error(true).errorCode(NerLabelEditStatusDto.ErrorCode.DUPLICATE_NAME)
							.build();
				}

				labelBuilder = label.toBuilder();
			}else{
				if (labelFoundByNameOpt.isPresent()){
					return NerLabelEditStatusDto.builder().error(true).errorCode(NerLabelEditStatusDto.ErrorCode.DUPLICATE_NAME)
							.build();
				}

				labelBuilder = NerLabel.builder()
						.created(Calendar.getInstance())
						.job(labelingJob);
			}

			final NerLabel nerLabel = labelBuilder.name(nerLabelDto.getName())
					.description(nerLabelDto.getDescription())
					.updated(Calendar.getInstance())
					.build();

			return NerLabelEditStatusDto.builder()
					.error(false)
					.nerLabelDto(toDto(nerLabel))
					.build();
		}catch (Exception e){
			throw new NerServiceException(e);
		}
	}

	/**
	 * Delete labeling task by id
	 *
	 * @param labelingJob
	 * @param labelId
	 * @throws NerServiceException
	 */
	@Override
	public void deleteLabel(LabelingJob labelingJob, Integer labelId) throws NerServiceException {
		try{
			final Optional<NerLabel> labelOpt = this.nerLabelsRepository.findById(labelId.longValue());

			if (! labelOpt.isPresent()){
				throw new NerServiceException(String.format("Can not find ner label by id=%d that belongs " +
						"to task %d (%s) ", labelId, labelingJob.getId(), labelingJob.getName()));
			}

			final NerLabel label = labelOpt.get();
			if(! label.getJob().equals(labelingJob)){
				throw new NerServiceException(String.format("Attempt to edit Ner label id=%d that belongs " +
								"to task %d (%s)  from task %d (%s) session", labelId, label.getJob().getId(),
						label.getJob().getName(), labelingJob.getId(), labelingJob.getName()));
			}

			this.nerLabelsRepository.delete(label);

		}catch (Exception e){
			throw new NerServiceException(e);
		}
	}


	private NerLabelDto toDto(final NerLabel nerJobAvailableEntity){
		 return  NerLabelDto.builder()
				 .id(nerJobAvailableEntity.getId() != null ? nerJobAvailableEntity.getId().intValue() : null)
				 .name(nerJobAvailableEntity.getName())
				 .description(nerJobAvailableEntity.getDescription())
				 .build();

	}
}
