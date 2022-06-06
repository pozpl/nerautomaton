package com.pozpl.nerannotator.ner.impl.management.job;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NerJobSaveStatusDto {

	private SaveStatus status;

	private ErrorCode errorCode;

	private NerJobDto nerJobDto;

	public enum SaveStatus{
		OK,
		ERROR
	}


	public enum ErrorCode{
		NAME_ALREADY_EXISTS
	}
}
