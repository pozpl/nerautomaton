package com.pozpl.nerannotator.controllers.ner;

import com.pozpl.nerannotator.controllers.exceptions.NerWebException;
import com.pozpl.nerannotator.persistence.model.User;
import com.pozpl.nerannotator.service.exceptions.NerServiceException;
import com.pozpl.nerannotator.service.ner.jobmanagement.text.IJobTextAccessService;
import com.pozpl.nerannotator.service.ner.jobmanagement.text.JobTextDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ner/text")
public class NerTextController {


	private final IJobTextAccessService jobTextAccessService;

	@Autowired
	public NerTextController(IJobTextAccessService jobTextAccessService) {
		this.jobTextAccessService = jobTextAccessService;
	}

	/**
	 * Get paginated list of texts for particular job
	 * @param page
	 * @param jobId
	 * @param user
	 * @return
	 */
	@GetMapping(value = "/list")
	@ResponseBody
	public Page<JobTextDto> list(@RequestParam("page") final Integer page,
								 @RequestParam("jobId") final Integer jobId,
								 final User user) {
		try {
			return jobTextAccessService.getTextForJob(jobId, page, user);
		} catch (NerServiceException e) {
			throw new NerWebException(e);
		}
	}
}
