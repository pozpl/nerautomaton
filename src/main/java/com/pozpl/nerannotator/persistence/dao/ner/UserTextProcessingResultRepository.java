package com.pozpl.nerannotator.persistence.dao.ner;

import com.pozpl.nerannotator.persistence.model.User;
import com.pozpl.nerannotator.persistence.model.job.LabelingJob;
import com.pozpl.nerannotator.persistence.model.ner.NerJobTextItem;
import com.pozpl.nerannotator.persistence.model.ner.UserNerTextProcessingResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTextProcessingResultRepository extends PagingAndSortingRepository<UserNerTextProcessingResult, Long> {

	@Query("SELECT tpr FROM UserNerTextProcessingResult AS tpr " +
			"JOIN NerJobTextItem as ti ON ti = tpr.textItem " +
			"WHERE tpr.user = :user AND ti.job = :job ")
	Page<UserNerTextProcessingResult> getForUserAndJob(@Param("user") User user,
													   @Param("job") LabelingJob job,
													   Pageable pageable);

	@Query("SELECT ti FROM NerJobTextItem as ti " +
			"LEFT JOIN UserNerTextProcessingResult as tpr ON ti = tpr.textItem AND tpr.user = :user " +
			"WHERE tpr IS NULL AND ti.job = :job ")
	Page<NerJobTextItem> getUnprocessed(@Param("user") User user,
										@Param("job") LabelingJob job,
										Pageable pageable);
}
