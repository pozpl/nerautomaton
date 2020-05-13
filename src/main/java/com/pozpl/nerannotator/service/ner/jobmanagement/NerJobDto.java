package com.pozpl.nerannotator.service.ner.jobmanagement;

import com.pozpl.nerannotator.service.ner.jobmanagement.labels.NerLabelDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NerJobDto {

	private Integer id;

	private String name;

	private Date created;

	private List<NerLabelDto> labels;
	
}
