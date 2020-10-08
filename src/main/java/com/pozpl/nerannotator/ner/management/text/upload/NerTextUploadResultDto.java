package com.pozpl.nerannotator.ner.management.text.upload;

public class NerTextUploadResultDto {

	private String status;
	private String error;

	public NerTextUploadResultDto(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public static NerTextUploadResultDto ok(){
		return new NerTextUploadResultDto("ok");
	}

	public static NerTextUploadResultDto error(final String error){
		final NerTextUploadResultDto dto = new NerTextUploadResultDto("error");
		dto.setError(error);

		return dto;
	}
}
