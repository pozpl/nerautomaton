package com.pozpl.nerannotator.ner.dao.repo.job;

import com.pozpl.nerannotator.auth.dao.model.User;
import com.pozpl.nerannotator.ner.dao.model.job.LabelingJob;
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
	Page<LabelingJob> getJobsForOwner(@Param("owner") User owner, Pageable pageable);


	@Query("SELECT j FROM LabelingJob AS j WHERE j.owner = :owner AND j.id = :id")
	Optional<LabelingJob> findByIdAndOwner(@Param("id") Long id, @Param("owner") User user);

	@Query("SELECT u FROM LabelingJob  as j " +
			"JOIN j.owner AS u " +
			"WHERE j = :job ")
	User getOwnerForJob(@Param("job") LabelingJob job);
}