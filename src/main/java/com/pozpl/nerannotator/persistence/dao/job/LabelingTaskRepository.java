package com.pozpl.nerannotator.persistence.dao.job;

import com.pozpl.nerannotator.persistence.model.User;
import com.pozpl.nerannotator.persistence.model.job.LabelingTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LabelingTaskRepository extends PagingAndSortingRepository<LabelingTask, Long> {

	@Query("SELECT j FROM LabelingTask AS j WHERE j.owner = :owner")
	Page<LabelingTask> getJobsForOwner(@Param("owner") User owner, Pageable pageable);


	@Query("SELECT j FROM LabelingTask AS j WHERE j.owner = :owner AND j.id = :id")
	Optional<LabelingTask> findByIdAndOwner(@Param("id") Long id, @Param("owner") User user);
}
