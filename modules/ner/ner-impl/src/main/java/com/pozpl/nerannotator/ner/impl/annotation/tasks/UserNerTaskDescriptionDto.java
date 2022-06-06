package com.pozpl.nerannotator.ner.impl.annotation.tasks;

import com.pozpl.nerannotator.ner.impl.management.job.NerJobDto;

public class UserNerTaskDescriptionDto {

	private NerJobDto job;

	private int processed;

	private int unprocessed;

	public UserNerTaskDescriptionDto(NerJobDto job, int processed, int unprocessed) {
		this.job = job;
		this.processed = processed;
		this.unprocessed = unprocessed;
	}

	public NerJobDto getJob() {
		return job;
	}

	public int getProcessed() {
		return processed;
	}

	public int getUnprocessed() {
		return unprocessed;
	}
}
