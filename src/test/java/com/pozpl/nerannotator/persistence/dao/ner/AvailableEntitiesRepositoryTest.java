package com.pozpl.nerannotator.persistence.dao.ner;

import com.pozpl.nerannotator.NerAnnotatorApplicationTests;
import com.pozpl.nerannotator.persistence.dao.UserRepository;
import com.pozpl.nerannotator.persistence.dao.job.LabelingTaskRepository;
import com.pozpl.nerannotator.persistence.model.LanguageCodes;
import com.pozpl.nerannotator.persistence.model.User;
import com.pozpl.nerannotator.persistence.model.job.LabelingTask;
import com.pozpl.nerannotator.persistence.model.ner.NerJobAvailableEntity;
import com.pozpl.nerannotator.persistence.model.ner.NerJobTextItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		classes = { NerAnnotatorApplicationTests.class },
		loader = AnnotationConfigContextLoader.class)
@Transactional
public class AvailableEntitiesRepositoryTest {

	@Autowired
	public UserRepository userRepository;

	@Autowired
	public LabelingTaskRepository labelingTaskRepository;

	@Autowired
	public AvailableEntitiesRepository availableEntitiesRepository;

	private User userOne;

	private LabelingTask jobOne;
	private LabelingTask jobTwo;

	private NerJobAvailableEntity jobOneEntityOne;
	private NerJobAvailableEntity jobOneEntityTwo;

	private NerJobAvailableEntity jobTwoEntityOne;

	@Before
	public void setUp() throws Exception {

		userOne = new User("username_one", "password_2", "First Name", "Last Name", "email@example");

		userRepository.save(userOne);

		Calendar hourAgo = Calendar.getInstance();
		hourAgo.add(Calendar.HOUR, -1);

		Calendar now = Calendar.getInstance();

		jobOne = LabelingTask.builder().name("ner job One").owner(userOne).languageCode( LanguageCodes.EN)
				.updated(hourAgo).created( hourAgo).build();
		jobTwo = LabelingTask.builder().name("ner job Two").owner(userOne).languageCode(LanguageCodes.EN)
				.created(now).updated( now).build();

		labelingTaskRepository.save(jobOne);
		labelingTaskRepository.save(jobTwo);

		jobOneEntityOne = NerJobAvailableEntity.builder()
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

	@After
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
		List<NerJobAvailableEntity> labels = availableEntitiesRepository.getForJob(jobOne);

		assertNotNull(labels);
		assertEquals(2, labels.size());

		assertEquals(jobOneEntityOne, labels.get(0));
	}

}