package com.pozpl.nerannotator.persistence.model.ner;

import com.pozpl.nerannotator.persistence.model.job.LabelingJob;
import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;


@Data
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

	public NerJobTextItem() {
	}

	public NerJobTextItem(LabelingJob job, String text, String md5Hash) {
		this.job = job;
		this.text = text;
		this.md5Hash = md5Hash;
		this.created = Calendar.getInstance();
		this.updated = this.created;
	}

	public NerJobTextItem(LabelingJob job, String text, String md5Hash, String tokens) {
		this.job = job;
		this.text = text;
		this.md5Hash = md5Hash;
		this.tokens = tokens;
		this.created = Calendar.getInstance();
		this.updated = this.created;
	}

	public NerJobTextItem(LabelingJob job, String text, String md5Hash, String tokens, Calendar created, Calendar updated) {
		this.job = job;
		this.text = text;
		this.md5Hash = md5Hash;
		this.tokens = tokens;
		this.created = created;
		this.updated = updated;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LabelingJob getJob() {
		return job;
	}

	public void setJob(LabelingJob job) {
		this.job = job;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getMd5Hash() {
		return md5Hash;
	}

	public void setMd5Hash(String md5Hash) {
		this.md5Hash = md5Hash;
	}

	public String getTokens() {
		return tokens;
	}

	public void setTokens(String tokens) {
		this.tokens = tokens;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	public Calendar getUpdated() {
		return updated;
	}

	public void setUpdated(Calendar updated) {
		this.updated = updated;
	}
}
