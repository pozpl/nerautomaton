package com.pozpl.nerannotator.persistence.dao.ner;

import com.pozpl.nerannotator.NerAnnotatorApplicationTests;
import auth.dao.repo.UserRepository;
import com.pozpl.nerannotator.ner.dao.repo.job.LabelingJobsRepository;
import com.pozpl.nerannotator.ner.dao.model.LanguageCodes;
import auth.dao.model.User;
import com.pozpl.nerannotator.ner.dao.model.job.LabelingJob;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
public class LabelingTaskRepositoryTest {

	@Autowired
	public UserRepository userRepository;

	@Autowired
	public LabelingJobsRepository labelingTaskRepository;

	private User userOne;

	private LabelingJob jobOne;
	private LabelingJob jobTwo;

	@BeforeEach
	public void setUp() throws Exception {

		userOne = new User("username_one", "password_2", "First Name", "Last Name", "email@example");

		userRepository.save(userOne);

		Calendar hourAgo = Calendar.getInstance();
		hourAgo.add(Calendar.HOUR, -1);

		Calendar now = Calendar.getInstance();

		jobOne = LabelingJob.builder().name("ner job One").owner(userOne).languageCode( LanguageCodes.EN)
				.updated(hourAgo).created( hourAgo).build();
		jobTwo = LabelingJob.builder().name("ner job Two").owner(userOne).languageCode(LanguageCodes.EN)
				.created(now).updated( now).build();

		labelingTaskRepository.save(jobOne);
		labelingTaskRepository.save(jobTwo);
	}



	@AfterEach
	public void tearDown() throws Exception {

		labelingTaskRepository.delete(jobOne);
		labelingTaskRepository.delete(jobTwo);

		userRepository.delete(userOne);

	}


	@Test
	public void getJobs() throws Exception{
		Page<LabelingJob> jobsPage = labelingTaskRepository.getJobsForOwner(userOne, PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "created")));


		assertNotNull(jobsPage);
		assertEquals(2, jobsPage.getNumberOfElements());
		assertEquals(1, jobsPage.getTotalPages());
		assertEquals(2, jobsPage.getTotalElements());
		List<LabelingJob> jobs =  jobsPage.getContent();
		assertNotNull(jobs);
		assertEquals(jobOne, jobs.get(0));
	}
}