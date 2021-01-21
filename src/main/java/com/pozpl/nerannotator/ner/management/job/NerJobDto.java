package com.pozpl.nerannotator.ner.management.job;

import com.pozpl.nerannotator.ner.management.labels.NerLabelDto;
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

	private Integer unprocessed;
	private Integer processed;
	
}
