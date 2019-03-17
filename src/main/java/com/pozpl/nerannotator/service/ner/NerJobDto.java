package com.pozpl.nerannotator.service.ner;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder(toBuilder = true)
public class NerJobDto {

	private Integer id;

	private String name;

	private Date created;

}
