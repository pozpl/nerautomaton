package com.pozpl.nerannotator.persistence.model.ner;

import com.pozpl.nerannotator.persistence.model.job.LabelingJob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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


	@CreationTimestamp
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "created", insertable = false, updatable = false)
	private Calendar created;

	@UpdateTimestamp
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "updated", nullable = false)
	private Calendar updated;

	public static NerJobTextItem of(LabelingJob job, String text, String md5Hash){
		return new NerJobTextItemBuilder()
				.job(job)
				.text(text)
				.md5Hash(md5Hash).build()  ;
	}
	
}
