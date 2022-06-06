package com.pozpl.nerannotator.ner.impl.management.text.upload.txtfile;

import static org.junit.jupiter.api.Assertions.*;

import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.nerannotator.ner.impl.dao.model.job.LabelingJob;
import com.pozpl.nerannotator.ner.impl.dao.model.text.NerJobTextItem;
import com.pozpl.nerannotator.ner.impl.dao.model.user.UserId;
import com.pozpl.nerannotator.ner.impl.dao.repo.job.LabelingJobsRepository;
import com.pozpl.nerannotator.ner.impl.dao.repo.text.NerJobTextItemRepository;
import com.pozpl.nerannotator.ner.impl.management.text.upload.NerTextUploadResultDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TxtFileUploadServiceImplTest {

    private TxtFileUploadServiceImpl txtFileUploadService;

    @Mock
    private LabelingJobsRepository labelingJobsRepository;
    @Mock
    private NerJobTextItemRepository nerJobTextItemRepository;

    private MultipartFile file;

    private Integer jobId = 42;
    private UserIntDto user;
    private LabelingJob labelingJob;

    @BeforeEach
    public void setUp() throws Exception {
        txtFileUploadService = new TxtFileUploadServiceImpl(labelingJobsRepository, nerJobTextItemRepository);
        user = UserIntDto.of(123L, "test_user", "Test", "User", "", "");
        labelingJob = LabelingJob.builder().owner(UserId.of(user)).id(Long.valueOf(jobId)).build();

        final StringBuilder builder = new StringBuilder();
        builder.append("line1\n");
        builder.append("line2\n");
        builder.append("\n");
        builder.append("\n");
        builder.append("\n");
        builder.append("line3\n");
        builder.append("line4\n");

        file = new MockMultipartFile("test.txt","test.txt","text" ,builder.toString().getBytes("UTF-8"));
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    public void processFile() throws Exception{
        when(labelingJobsRepository.findById(Long.valueOf(jobId))).thenReturn(Optional.of(labelingJob));

        when(nerJobTextItemRepository.getForJobAndHash(ArgumentMatchers.eq(labelingJob), any())).thenReturn(Optional.empty());

        final ArgumentCaptor<NerJobTextItem> textsCaptor = ArgumentCaptor.forClass(NerJobTextItem.class);

        final NerTextUploadResultDto resultDto = txtFileUploadService.processFile(file, jobId, user);

        verify(nerJobTextItemRepository, times(2)).save(textsCaptor.capture());

        final List<NerJobTextItem> capturedTexts = textsCaptor.getAllValues();
        assertEquals(2, capturedTexts.size());

    }
}