package com.pozpl.nerannotator.persistence.dao.ner;


import com.pozpl.nerannotator.persistence.model.job.LabelingTask;
import com.pozpl.nerannotator.persistence.model.ner.NerJobAvailableEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailableEntitiesRepository extends PagingAndSortingRepository<NerJobAvailableEntity, Long> {

	@Query("SELECT ae FROM NerJobAvailableEntity AS ae WHERE ae.job = :job ORDER BY ae.name ASC")
	List<NerJobAvailableEntity>  getForJob(@Param("job") LabelingTask job);
	
}
