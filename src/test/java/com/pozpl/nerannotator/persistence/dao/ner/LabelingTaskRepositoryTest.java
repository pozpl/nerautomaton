package com.pozpl.nerannotator.persistence.dao.ner;

import com.pozpl.neraannotator.user.api.IUserService;
import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.neraannotator.user.api.UserSaveResultDto;
import com.pozpl.nerannotator.NerAnnotatorApplicationTests;
import com.pozpl.nerannotator.ner.impl.dao.model.LanguageCodes;
import com.pozpl.nerannotator.ner.impl.dao.model.job.LabelingJob;
import com.pozpl.nerannotator.ner.impl.dao.model.user.UserId;
import com.pozpl.nerannotator.ner.impl.dao.repo.job.LabelingJobsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
	public IUserService userRepository;

	@Autowired
	public LabelingJobsRepository labelingTaskRepository;

	private UserIntDto userOne;

	private LabelingJob jobOne;
	private LabelingJob jobTwo;

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
	}



	@AfterEach
	public void tearDown() throws Exception {

		labelingTaskRepository.delete(jobOne);
		labelingTaskRepository.delete(jobTwo);

		userRepository.delete(userOne);

	}


	@Test
	public void getJobs() throws Exception{
		Page<LabelingJob> jobsPage = labelingTaskRepository.getJobsForOwner(UserId.of(userOne), PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "created")));


		assertNotNull(jobsPage);
		assertEquals(2, jobsPage.getNumberOfElements());
		assertEquals(1, jobsPage.getTotalPages());
		assertEquals(2, jobsPage.getTotalElements());
		List<LabelingJob> jobs =  jobsPage.getContent();
		assertNotNull(jobs);
		Assertions.assertEquals(jobOne, jobs.get(0));
	}
}