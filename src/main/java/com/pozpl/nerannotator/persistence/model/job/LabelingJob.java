package com.pozpl.nerannotator.persistence.model.job;

import com.pozpl.nerannotator.persistence.model.LanguageCodes;
import com.pozpl.nerannotator.persistence.model.User;
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
@Table(name = "ner_jobs",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "owner_id", "name" }) })
public class LabelingJob {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
}
