package com.pozpl.nerannotator.ner.impl.annotation.tasks.labels;

import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.nerannotator.ner.impl.management.labels.NerLabelDto;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.shared.exceptions.NerWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/ner/labels")
public class JobLabelsAccessController {

	private IJobLabelsAccessService jobLabelsAccessService;

	@Autowired
	public JobLabelsAccessController(IJobLabelsAccessService jobLabelsAccessService) {
		this.jobLabelsAccessService = jobLabelsAccessService;
	}

	@RequestMapping(value = "/get/for/job")
	public List<NerLabelDto> getLabels(@RequestParam(name = "jobId") Integer jobId,
									   UserIntDto user) {
		try {
			return jobLabelsAccessService.getLabels(jobId, user);
		} catch (NerServiceException e) {
			throw new NerWebException(e);
		}
	}

}
