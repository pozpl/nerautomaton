package com.pozpl.nerannotator.persistence.dao.ner;

import com.pozpl.nerannotator.NerAnnotatorApplicationTests;
import com.pozpl.nerannotator.persistence.dao.UserRepository;
import com.pozpl.nerannotator.persistence.dao.job.LabelingTaskRepository;
import com.pozpl.nerannotator.persistence.model.LanguageCodes;
import com.pozpl.nerannotator.persistence.model.User;
import com.pozpl.nerannotator.persistence.model.job.LabelingTask;
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

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		classes = { NerAnnotatorApplicationTests.class },
		loader = AnnotationConfigContextLoader.class)
@Transactional
public class NerTextItemRepositoryTest {

	@Autowired
	public UserRepository userRepository;

	@Autowired
	public LabelingTaskRepository labelingTaskRepository;

	@Autowired
	public NerJobTextItemRepository nerJobTextItemRepository;

	private User userOne;

	private LabelingTask jobOne;
	private LabelingTask jobTwo;

	private NerJobTextItem jobOneTextOne;
	private NerJobTextItem jobOneTextTwo;

	private NerJobTextItem jobTwoTextOne;

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

		jobOneTextOne = new NerJobTextItem(jobOne, "text_one", "hash_one");
		jobOneTextTwo = new NerJobTextItem(jobOne, "text_two", "hash_two");
		jobOneTextTwo.setCreated(hourAgo);
		jobOneTextTwo.setUpdated(hourAgo);

		jobTwoTextOne = new NerJobTextItem(jobTwo, "text_one", "hash_one");//same text different job

		nerJobTextItemRepository.save(jobOneTextOne);
		nerJobTextItemRepository.save(jobOneTextTwo);
		nerJobTextItemRepository.save(jobTwoTextOne);
	}

	@After
	public void tearDown() throws Exception {

		nerJobTextItemRepository.delete(jobOneTextOne);
		nerJobTextItemRepository.delete(jobOneTextTwo);
		nerJobTextItemRepository.delete(jobTwoTextOne);

		labelingTaskRepository.delete(jobOne);
		labelingTaskRepository.delete(jobTwo);

		userRepository.delete(userOne);
	}

	@Test
	public void testGetForJob(){
		Page<NerJobTextItem> textItemsPage = nerJobTextItemRepository.getForJob(jobOne, PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "created")));


		assertNotNull(textItemsPage);
		assertEquals(2, textItemsPage.getNumberOfElements());
		assertEquals(1, textItemsPage.getTotalPages());
		assertEquals(2, textItemsPage.getTotalElements());
		List<NerJobTextItem> jobs =  textItemsPage.getContent();
		assertNotNull(jobs);
		assertEquals(jobOneTextTwo, jobs.get(0));
	}
}