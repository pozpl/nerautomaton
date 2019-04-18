package com.pozpl.nerannotator.persistence.dao.ner;


import com.pozpl.nerannotator.persistence.model.job.LabelingJob;
import com.pozpl.nerannotator.persistence.model.ner.NerLabel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NerLabelsRepository extends PagingAndSortingRepository<NerLabel, Long> {

	@Query("SELECT ae FROM NerLabel AS ae WHERE ae.job = :job ORDER BY ae.name ASC")
	List<NerLabel>  getForJob(@Param("job") LabelingJob job);


	@Query("SELECT ae FROM NerLabel AS ae WHERE ae.job = :job " +
			"AND ae.name = :name " +
			"ORDER BY ae.name ASC")
	Optional<NerLabel> getByNameAndJob(@Param("name") String name, @Param("job") LabelingJob job);
	
}
