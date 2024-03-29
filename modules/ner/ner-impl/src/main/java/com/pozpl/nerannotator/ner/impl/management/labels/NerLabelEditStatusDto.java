package com.pozpl.nerannotator.ner.impl.management.labels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NerLabelEditStatusDto {

	private NerLabelDto nerLabelDto;

	private boolean error;

	private ErrorCode errorCode;


	public enum ErrorCode{
		DUPLICATE_NAME,
	}
}
