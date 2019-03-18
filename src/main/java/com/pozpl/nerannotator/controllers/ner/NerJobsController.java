package com.pozpl.nerannotator.controllers.ner;


import com.pozpl.nerannotator.controllers.exceptions.NerWebException;
import com.pozpl.nerannotator.persistence.model.User;
import com.pozpl.nerannotator.service.exceptions.NerServiceException;
import com.pozpl.nerannotator.service.ner.INerJobService;
import com.pozpl.nerannotator.service.ner.NerJobDto;
import com.pozpl.nerannotator.service.ner.NerJobSaveStatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ner/edit")
public class NerJobsController {

	private final INerJobService nerJobService;

	@Autowired
	public NerJobsController(final INerJobService nerJobService) {
		this.nerJobService = nerJobService;
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public NerJobDto get(@RequestParam("id") Integer id, User user) {

		try {
			return nerJobService.getJobById(id, user).get();
		} catch (NerServiceException e) {
			throw new NerWebException(e);
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Page<NerJobDto> list(@RequestParam("page") Integer page
								, User user) {

		try {
			return nerJobService.getJobsForOwner(user, page);
		} catch (NerServiceException e) {
			throw new NerWebException(e);
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public NerJobSaveStatusDto save(NerJobDto nerJobDto, User user){
		try{
			return nerJobService.saveJob(nerJobDto, user);
		}catch (NerServiceException e){
			throw new NerWebException(e);
		}
	}
}
