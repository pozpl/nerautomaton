package com.pozpl.nerannotator.ner.management.text;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobTextDto {

	private Integer id;

	private Integer jobId;

	private String text;
}
