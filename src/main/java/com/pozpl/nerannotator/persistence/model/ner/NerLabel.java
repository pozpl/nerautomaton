package com.pozpl.nerannotator.persistence.model.ner;


import com.pozpl.nerannotator.persistence.model.job.LabelingJob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ner_job_available_entities",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "job_id", "name" }) })
public class NerLabel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;


	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "job_id")
	private LabelingJob job;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description")
	private String description;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false, updatable = false)
	private Calendar created;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "updated", nullable = false)
	private Calendar updated;

	
}
