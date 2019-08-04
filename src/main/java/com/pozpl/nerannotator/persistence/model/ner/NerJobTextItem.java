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
@Table(name = "ner_jobs_texts",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "job_id", "md5_hash" }) })
public class NerJobTextItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "job_id")
	private LabelingJob job;

	@Column(name = "text", nullable = false)
	private String text;

	@Column(name = "md5_hash", nullable = false)
	private String md5Hash;

	/**
	 * Tokens for given text
	 */
	@Column(name = "tokens")
	private String tokens;


	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false, updatable = false)
	private Calendar created;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "updated", nullable = false)
	private Calendar updated;
	
}
