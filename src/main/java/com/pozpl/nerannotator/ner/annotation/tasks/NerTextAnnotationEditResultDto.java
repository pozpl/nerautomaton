package com.pozpl.nerannotator.ner.annotation.tasks;

public class NerTextAnnotationEditResultDto {

	private NerTextAnnotationDto editResult;

	private boolean status;

	private String error;

	public NerTextAnnotationEditResultDto(NerTextAnnotationDto editResult) {
		this.editResult = editResult;
		this.status = true;
	}

	public NerTextAnnotationEditResultDto(String error) {
		this.error = error;
		this.status = false;
	}

	public NerTextAnnotationDto getEditResult() {
		return editResult;
	}

	public boolean isStatus() {
		return status;
	}

	public String getError() {
		return error;
	}


	public static NerTextAnnotationEditResultDto success(NerTextAnnotationDto editResult) {
		return  new NerTextAnnotationEditResultDto(editResult);
	}

	public static NerTextAnnotationEditResultDto error(String error){
		return new NerTextAnnotationEditResultDto(error);
	}
}
