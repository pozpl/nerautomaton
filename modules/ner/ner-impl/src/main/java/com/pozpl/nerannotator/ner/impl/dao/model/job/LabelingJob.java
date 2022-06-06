package com.pozpl.nerannotator.ner.impl.dao.model.job;

import com.pozpl.nerannotator.ner.impl.dao.model.LanguageCodes;
import com.pozpl.nerannotator.ner.impl.dao.model.user.UserId;
import com.pozpl.nerannotator.ner.impl.dao.model.user.UserIdConverter;
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

	@Column(name = "owner_id")
	@Convert(converter = UserIdConverter.class)
	private UserId owner;

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
