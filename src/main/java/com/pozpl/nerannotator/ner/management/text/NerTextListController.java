package com.pozpl.nerannotator.ner.management.text;

import auth.dao.model.User;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.shared.exceptions.NerWebException;
import com.pozpl.nerannotator.shared.pagination.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ner/text")
public class NerTextListController {


	private final IJobTextAccessService jobTextAccessService;

	@Autowired
	public NerTextListController(IJobTextAccessService jobTextAccessService) {
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
	public PageDto<JobTextDto> list(@RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
									@RequestParam("jobId") final Integer jobId,
									final User user) {
		try {
			return jobTextAccessService.getTextForJob(jobId, page, user);
		} catch (NerServiceException e) {
			throw new NerWebException(e);
		}
	}
}
