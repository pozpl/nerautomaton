package com.pozpl.nerannotator.ner.dao.model.text;

import com.pozpl.nerannotator.auth.dao.model.User;
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
@Table(name = "ner_user_text_processing_result",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "text_id" }) })
public class UserNerTextProcessingResult {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "text_id")
	private NerJobTextItem textItem;

	@Basic
	@Column(name = "annotated_text", nullable = false)
	private String annotatedText;

	@CreationTimestamp
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "created", insertable = false, updatable = false, columnDefinition = "timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP")
	private Calendar created;

	@UpdateTimestamp
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "updated", nullable = false, columnDefinition = "timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP")
	private Calendar updated;


}
