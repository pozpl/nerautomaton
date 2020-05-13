package com.pozpl.nerannotator.service.ner.annotation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TaggedTermDto {

	private String term;
	private String label;
	private String nerTag;
}
