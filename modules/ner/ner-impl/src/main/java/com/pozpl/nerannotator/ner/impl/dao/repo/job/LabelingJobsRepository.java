package com.pozpl.nerannotator.ner.impl.dao.repo.job;

import com.pozpl.nerannotator.ner.impl.dao.model.job.LabelingJob;
import com.pozpl.nerannotator.ner.impl.dao.model.user.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LabelingJobsRepository extends PagingAndSortingRepository<LabelingJob, Long> {

	@Query("SELECT j FROM LabelingJob AS j WHERE j.owner = :owner")
	Page<LabelingJob> getJobsForOwner(@Param("owner") UserId owner, Pageable pageable);


	@Query("SELECT j FROM LabelingJob AS j WHERE j.owner = :owner AND j.id = :id")
	Optional<LabelingJob> findByIdAndOwner(@Param("id") Long id, @Param("owner") UserId user);

	@Query("SELECT j.owner FROM LabelingJob  as j " +
			"WHERE j = :job ")
	UserId getOwnerForJob(@Param("job") LabelingJob job);
}
