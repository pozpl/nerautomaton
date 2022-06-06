package com.pozpl.nerannotator.persistence.dao.ner;

import com.pozpl.neraannotator.user.api.IUserService;
import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.neraannotator.user.api.UserSaveResultDto;
import com.pozpl.nerannotator.NerAnnotatorApplicationTests;
import com.pozpl.nerannotator.ner.impl.dao.model.LanguageCodes;
import com.pozpl.nerannotator.ner.impl.dao.model.job.LabelingJob;
import com.pozpl.nerannotator.ner.impl.dao.model.text.NerLabel;
import com.pozpl.nerannotator.ner.impl.dao.model.user.UserId;
import com.pozpl.nerannotator.ner.impl.dao.repo.job.LabelingJobsRepository;
import com.pozpl.nerannotator.ner.impl.dao.repo.text.NerLabelsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
		classes = { NerAnnotatorApplicationTests.class },
		loader = AnnotationConfigContextLoader.class)
@Transactional
public class AvailableEntitiesRepositoryTest {

	@Autowired
	public IUserService userRepository;

	@Autowired
	public LabelingJobsRepository labelingTaskRepository;

	@Autowired
	public NerLabelsRepository availableEntitiesRepository;

	private UserIntDto userOne;

	private LabelingJob jobOne;
	private LabelingJob jobTwo;

	private NerLabel jobOneEntityOne;
	private NerLabel jobOneEntityTwo;

	private NerLabel jobTwoEntityOne;

	@BeforeEach
	public void setUp() throws Exception {

		userOne = UserIntDto.create("username_one", "password_2", "First Name", "Last Name", "email@example");

		final UserSaveResultDto saveResultDto = userRepository.save(userOne);
		userOne = saveResultDto.getUser();

		Calendar hourAgo = Calendar.getInstance();
		hourAgo.add(Calendar.HOUR, -1);

		Calendar now = Calendar.getInstance();

		jobOne = LabelingJob.builder().name("ner job One").owner(UserId.of(userOne)).languageCode( LanguageCodes.EN)
				.updated(hourAgo).created( hourAgo).build();
		jobTwo = LabelingJob.builder().name("ner job Two").owner(UserId.of(userOne)).languageCode(LanguageCodes.EN)
				.created(now).updated( now).build();

		labelingTaskRepository.save(jobOne);
		labelingTaskRepository.save(jobTwo);

		jobOneEntityOne = NerLabel.builder()
				.job(jobOne)
		.created(now)
		.updated(now)
		.name("Annotation1").build();
		jobOneEntityTwo = jobOneEntityOne.toBuilder().name("Annotation2").description("This one have descriiption")
				.created(hourAgo).updated(hourAgo).build();

		jobTwoEntityOne = jobOneEntityOne.toBuilder().job(jobTwo).build();//complete copy of annotation1 but for different job

		availableEntitiesRepository.save(jobOneEntityOne);
		availableEntitiesRepository.save(jobOneEntityTwo);
		availableEntitiesRepository.save(jobTwoEntityOne);
	}

	@AfterEach
	public void tearDown() throws Exception {

		availableEntitiesRepository.delete(jobOneEntityOne);
		availableEntitiesRepository.delete(jobOneEntityTwo);
		availableEntitiesRepository.delete(jobTwoEntityOne);

		labelingTaskRepository.delete(jobOne);
		labelingTaskRepository.delete(jobTwo);

		userRepository.delete(userOne);
	}

	@Test
	public void testGetForJob() throws Exception{
		List<NerLabel> labels = availableEntitiesRepository.getForJob(jobOne);

		assertNotNull(labels);
		assertEquals(2, labels.size());

		Assertions.assertEquals(jobOneEntityOne, labels.get(0));
	}

}
