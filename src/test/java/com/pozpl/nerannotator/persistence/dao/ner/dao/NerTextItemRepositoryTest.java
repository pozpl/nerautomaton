package com.pozpl.nerannotator.persistence.dao.ner.dao;

import com.pozpl.neraannotator.user.api.IUserService;
import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.neraannotator.user.api.UserSaveResultDto;
import com.pozpl.nerannotator.NerAnnotatorApplicationTests;
import com.pozpl.nerannotator.ner.impl.dao.model.LanguageCodes;
import com.pozpl.nerannotator.ner.impl.dao.model.job.LabelingJob;
import com.pozpl.nerannotator.ner.impl.dao.model.text.NerJobTextItem;
import com.pozpl.nerannotator.ner.impl.dao.model.user.UserId;
import com.pozpl.nerannotator.ner.impl.dao.repo.job.LabelingJobsRepository;
import com.pozpl.nerannotator.ner.impl.dao.repo.text.NerJobTextItemRepository;
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
public class NerTextItemRepositoryTest {

	@Autowired
	public IUserService userRepository;

	@Autowired
	public LabelingJobsRepository labelingTaskRepository;

	@Autowired
	public NerJobTextItemRepository nerJobTextItemRepository;

	private UserIntDto userOne;

	private LabelingJob jobOne;
	private LabelingJob jobTwo;

	private NerJobTextItem jobOneTextOne;
	private NerJobTextItem jobOneTextTwo;

	private NerJobTextItem jobTwoTextOne;

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

		jobOneTextOne = NerJobTextItem.of(jobOne, "text_one", "hash_one");
		jobOneTextTwo = NerJobTextItem.of(jobOne, "text_two", "hash_two");

		jobTwoTextOne = NerJobTextItem.of(jobTwo, "text_one", "hash_one");//same text different job

		nerJobTextItemRepository.save(jobOneTextOne);
		nerJobTextItemRepository.save(jobOneTextTwo);
		nerJobTextItemRepository.save(jobTwoTextOne);
	}

	@AfterEach
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
		Page<NerJobTextItem> textItemsPage = nerJobTextItemRepository.getForJob(jobOne, PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "created", "id")));


		assertNotNull(textItemsPage);
		assertEquals(2, textItemsPage.getNumberOfElements());
		assertEquals(1, textItemsPage.getTotalPages());
		assertEquals(2, textItemsPage.getTotalElements());
		List<NerJobTextItem> jobs =  textItemsPage.getContent();
		assertNotNull(jobs);
		Assertions.assertEquals(jobOneTextOne, jobs.get(0));
		Assertions.assertEquals(jobOneTextTwo, jobs.get(1));
	}
}