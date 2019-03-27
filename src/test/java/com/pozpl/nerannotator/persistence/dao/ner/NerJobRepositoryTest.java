package com.pozpl.nerannotator.persistence.dao.ner;

import com.pozpl.nerannotator.NerAnnotatorApplicationTests;
import com.pozpl.nerannotator.persistence.dao.UserRepository;
import com.pozpl.nerannotator.persistence.model.LanguageCodes;
import com.pozpl.nerannotator.persistence.model.User;
import com.pozpl.nerannotator.persistence.model.ner.NerJob;
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
public class NerJobRepositoryTest {

	@Autowired
	public UserRepository userRepository;

	@Autowired
	public NerJobRepository nerJobRepository;

	private User userOne;

	private NerJob jobOne;
	private NerJob jobTwo;

	@Before
	public void setUp() throws Exception {

		userOne = new User("username_one", "password_2", "First Name", "Last Name", "email@example");

		userRepository.save(userOne);

		Calendar hourAgo = Calendar.getInstance();
		hourAgo.add(Calendar.HOUR, -1);

		Calendar now = Calendar.getInstance();

		jobOne = NerJob.builder().name("ner job One").owner(userOne).languageCode( LanguageCodes.EN)
				.updated(hourAgo).created( hourAgo).build();
		jobTwo = NerJob.builder().name("ner job Two").owner(userOne).languageCode(LanguageCodes.EN)
				.created(now).updated( now).build();

		nerJobRepository.save(jobOne);
		nerJobRepository.save(jobTwo);
	}



	@After
	public void tearDown() throws Exception {

		nerJobRepository.delete(jobOne);
		nerJobRepository.delete(jobTwo);

		userRepository.delete(userOne);

	}


	@Test
	public void getJobs() throws Exception{
		Page<NerJob> jobsPage = nerJobRepository.getJobsForOwner(userOne, PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "created")));


		assertNotNull(jobsPage);
		assertEquals(2, jobsPage.getNumberOfElements());
		assertEquals(1, jobsPage.getTotalPages());
		assertEquals(2, jobsPage.getTotalElements());
		List<NerJob> jobs =  jobsPage.getContent();
		assertNotNull(jobs);
		assertEquals(jobOne, jobs.get(0));
	}
}