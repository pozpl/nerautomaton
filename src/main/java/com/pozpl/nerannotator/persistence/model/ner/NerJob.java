package com.pozpl.nerannotator.persistence.model.ner;

import com.pozpl.nerannotator.persistence.model.LanguageCodes;
import com.pozpl.nerannotator.persistence.model.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;

@Data
@Entity
@Table(name = "ner_job",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "owner_id", "name" }) })
public class NerJob {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;


	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	private User owner;

	@Enumerated(EnumType.STRING)
	@Column(name = "lang", nullable = false)
	private LanguageCodes languageCode;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false, updatable = false)
	private Calendar created;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "updated", nullable = false)
	private Calendar updated;

	public NerJob() {
	}

	public NerJob(final String name,
				  final User owner,
				  final LanguageCodes languageCode,
				  final Calendar created,
				  final Calendar updated) {
		this.name = name;
		this.owner = owner;
		this.languageCode = languageCode;
		this.created = created;
		this.updated = updated;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public LanguageCodes getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(LanguageCodes languageCode) {
		this.languageCode = languageCode;
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
