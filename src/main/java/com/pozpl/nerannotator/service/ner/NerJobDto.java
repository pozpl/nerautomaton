package com.pozpl.nerannotator.service.ner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NerJobDto {

	private Integer id;

	private String name;

	private Date created;
	
}
