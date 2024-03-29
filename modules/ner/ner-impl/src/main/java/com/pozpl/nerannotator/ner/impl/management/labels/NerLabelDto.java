package com.pozpl.nerannotator.ner.impl.management.labels;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NerLabelDto {

	private Integer id;

	private String name;

	private String description;
}
