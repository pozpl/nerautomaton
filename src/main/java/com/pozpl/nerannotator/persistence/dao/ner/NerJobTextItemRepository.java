package com.pozpl.nerannotator.persistence.dao.ner;

import com.pozpl.nerannotator.persistence.model.job.LabelingJob;
import com.pozpl.nerannotator.persistence.model.ner.NerJobTextItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NerJobTextItemRepository extends PagingAndSortingRepository<NerJobTextItem, Long> {

	@Query("SELECT jti FROM NerJobTextItem AS jti WHERE jti.job = :job")
	Page<NerJobTextItem> getForJob(@Param("job") LabelingJob job, Pageable pageable);


	@Query("SELECT jti FROM NerJobTextItem AS jti WHERE jti.job = :job AND jti.md5Hash = :md5hash ")
	NerJobTextItem getForJobAndHash(@Param("job") LabelingJob job,
									@Param("md5hash") String md5Hash);

}
