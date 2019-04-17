package com.pozpl.nerannotator.service.ner.labels;

import com.pozpl.nerannotator.persistence.dao.job.LabelingTaskRepository;
import com.pozpl.nerannotator.persistence.dao.ner.AvailableEntitiesRepository;
import com.pozpl.nerannotator.persistence.model.job.LabelingTask;
import com.pozpl.nerannotator.persistence.model.ner.NerJobAvailableEntity;
import com.pozpl.nerannotator.service.exceptions.NerServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class NerLabelEditingServiceImpl implements INerLabelEditingService {

	private LabelingTaskRepository labelingTaskRepository;

	private AvailableEntitiesRepository availableEntitiesRepository;

	@Autowired
	public NerLabelEditingServiceImpl(LabelingTaskRepository labelingTaskRepository,
									  AvailableEntitiesRepository availableEntitiesRepository) {
		this.labelingTaskRepository = labelingTaskRepository;
		this.availableEntitiesRepository = availableEntitiesRepository;
	}

	/**
	 * Get labels for job
	 *
	 * @param labelingTask
	 * @return
	 */
	@Override
	public List<NerLabelDto> listLabelsAvailableForTask(LabelingTask labelingTask) throws NerServiceException {
		try {
			final List<NerJobAvailableEntity> availableEntities = this.availableEntitiesRepository.getForJob(labelingTask);

			return  availableEntities.stream().map(entityLabel -> toDto(entityLabel)).collect(Collectors.toList());

		}catch (Exception e){
			throw new NerServiceException(e);
		}
	}

	/**
	 * Get labeling Dto by Id and ensure that it belongs to task
	 *
	 * @param id
	 * @param labelingTask
	 * @return
	 */
	@Override
	public Optional<NerLabelDto> getById(Integer id, LabelingTask labelingTask) throws NerServiceException {
		return Optional.empty();
	}

	/**
	 * Save Job
	 *
	 * @param nerLabelDto
	 * @param labelingTask
	 * @return
	 * @throws NerServiceException
	 */
	@Override
	public NerLabelDto saveLabel(NerLabelDto nerLabelDto, LabelingTask labelingTask) throws NerServiceException {
		return null;
	}

	/**
	 * Delete labeling task by id
	 *
	 * @param labelingTask
	 * @param labelingTaskId
	 * @throws NerServiceException
	 */
	@Override
	public void deleteLabel(LabelingTask labelingTask, Integer labelingTaskId) throws NerServiceException {

	}


	private NerLabelDto toDto(final NerJobAvailableEntity nerJobAvailableEntity){
		 return  NerLabelDto.builder()
				 .id(nerJobAvailableEntity.getId() != null ? nerJobAvailableEntity.getId().intValue() : null)
				 .name(nerJobAvailableEntity.getName())
				 .description(nerJobAvailableEntity.getDescription())
				 .build();

	}
}
