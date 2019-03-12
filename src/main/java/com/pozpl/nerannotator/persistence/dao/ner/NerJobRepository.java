package com.pozpl.nerannotator.persistence.dao.ner;

import com.pozpl.nerannotator.persistence.model.User;
import com.pozpl.nerannotator.persistence.model.ner.NerJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NerJobRepository extends PagingAndSortingRepository<NerJob, Long> {

	@Query("SELECT j FROM NerJob AS j WHERE j.owner = :owner")
	Page<NerJob> getJobsForOwner(@Param("owner") User owner, Pageable pageable);

}
