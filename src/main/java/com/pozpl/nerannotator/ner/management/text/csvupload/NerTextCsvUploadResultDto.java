package com.pozpl.nerannotator.ner.management.text.csvupload;

public class NerTextCsvUploadResultDto {

	private String status;
	private String error;

	public NerTextCsvUploadResultDto(String status) {
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

	public static NerTextCsvUploadResultDto ok(){
		return new NerTextCsvUploadResultDto("ok");
	}

	public static NerTextCsvUploadResultDto error(final String error){
		final NerTextCsvUploadResultDto dto = new NerTextCsvUploadResultDto("error");
		dto.setError(error);

		return dto;
	}
}
