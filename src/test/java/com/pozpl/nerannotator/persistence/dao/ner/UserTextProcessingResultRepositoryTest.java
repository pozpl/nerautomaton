package com.pozpl.nerannotator.persistence.dao.ner;

import com.pozpl.neraannotator.user.api.IUserService;
import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.neraannotator.user.api.UserSaveResultDto;
import com.pozpl.nerannotator.NerAnnotatorApplicationTests;
import com.pozpl.nerannotator.ner.impl.dao.model.LanguageCodes;
import com.pozpl.nerannotator.ner.impl.dao.model.job.LabelingJob;
import com.pozpl.nerannotator.ner.impl.dao.model.text.NerJobTextItem;
import com.pozpl.nerannotator.ner.impl.dao.model.text.UserNerTextProcessingResult;
import com.pozpl.nerannotator.ner.impl.dao.model.user.UserId;
import com.pozpl.nerannotator.ner.impl.dao.repo.job.LabelingJobsRepository;
import com.pozpl.nerannotator.ner.impl.dao.repo.text.NerJobTextItemRepository;
import com.pozpl.nerannotator.ner.impl.dao.repo.text.UserTextProcessingResultRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
		classes = { NerAnnotatorApplicationTests.class },
		loader = AnnotationConfigContextLoader.class)
@Transactional
public class UserTextProcessingResultRepositoryTest {

	@Autowired
	public IUserService userRepository;

	@Autowired
	public LabelingJobsRepository labelingTaskRepository;

	@Autowired
	public NerJobTextItemRepository nerJobTextItemRepository;

	@Autowired
	public UserTextProcessingResultRepository userTextProcessingResultRepository;

	private UserIntDto userOne;

	private LabelingJob jobOne;
	private LabelingJob jobTwo;

	private NerJobTextItem jobOneTextOne;
	private NerJobTextItem jobOneTextTwo;

	private NerJobTextItem jobTwoTextOne;

    private UserNerTextProcessingResult jobOneTextOneProcessed;

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
		jobOneTextTwo.setCreated(hourAgo);
		jobOneTextTwo.setUpdated(hourAgo);

		jobTwoTextOne = NerJobTextItem.of(jobTwo, "text_one", "hash_one");//same text different job

		nerJobTextItemRepository.save(jobOneTextOne);
		nerJobTextItemRepository.save(jobOneTextTwo);
		nerJobTextItemRepository.save(jobTwoTextOne);

		this.jobOneTextOneProcessed = UserNerTextProcessingResult.builder()
				.annotatedText("").textItem(jobOneTextOne).user(UserId.of(userOne)).build();
		this.userTextProcessingResultRepository.save(jobOneTextOneProcessed);
	}

	@AfterEach
	public void tearDown() throws Exception {
		this.userTextProcessingResultRepository.delete(jobOneTextOneProcessed);

		nerJobTextItemRepository.delete(jobOneTextOne);
		nerJobTextItemRepository.delete(jobOneTextTwo);
		nerJobTextItemRepository.delete(jobTwoTextOne);

		labelingTaskRepository.delete(jobOne);
		labelingTaskRepository.delete(jobTwo);

		userRepository.delete(userOne);
	}

	@Test
	public void getForUserAndJob() {
		final Page<UserNerTextProcessingResult> processed =  userTextProcessingResultRepository.getForUserAndJob(UserId.of(userOne), jobOne,
				PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "created")));

		assertNotNull(processed);
		assertEquals(1, processed.getNumberOfElements());
		final List<UserNerTextProcessingResult> content = processed.getContent();
		assertNotNull(content);
		Assertions.assertEquals(jobOneTextOneProcessed, content.get(0));
	}

	@Test
	public void getUnprocessed() {
		final List<NerJobTextItem> unprocessed = userTextProcessingResultRepository.getUnprocessed(UserId.of(userOne), jobOne,
				PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "id")));
		assertNotNull(unprocessed);
		assertEquals(1, unprocessed.size());
		Assertions.assertEquals(jobOneTextTwo, unprocessed.get(0));
	}

	@Test
	public void getForUserAndTextItem() {
		final Optional<UserNerTextProcessingResult> foundResult = userTextProcessingResultRepository.getForUserAndTextItem(UserId.of(userOne), jobOneTextOne);
		assertNotNull(foundResult);
		assertTrue(foundResult.isPresent());
		Assertions.assertEquals(jobOneTextOneProcessed, foundResult.get());

		final Optional<UserNerTextProcessingResult> notFound = userTextProcessingResultRepository.getForUserAndTextItem(UserId.of(userOne), jobOneTextTwo);
		assertFalse(notFound.isPresent());
	}
}