package com.pozpl.nerannotator.controllers.ner;

import com.pozpl.nerannotator.controllers.exceptions.NerWebException;
import com.pozpl.nerannotator.persistence.model.User;
import com.pozpl.nerannotator.service.exceptions.NerServiceException;
import com.pozpl.nerannotator.service.ner.text.IJobTextAccessService;
import com.pozpl.nerannotator.service.ner.text.JobTextDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@RequestMapping(value = "/list", method = RequestMethod.GET)
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
