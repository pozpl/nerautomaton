package com.pozpl.nerannotator.controllers.ner;


import com.pozpl.nerannotator.controllers.exceptions.NerWebException;
import com.pozpl.nerannotator.persistence.model.User;
import com.pozpl.nerannotator.service.exceptions.NerServiceException;
import com.pozpl.nerannotator.service.ner.jobmanagement.INerJobService;
import com.pozpl.nerannotator.service.ner.jobmanagement.NerJobDto;
import com.pozpl.nerannotator.service.ner.jobmanagement.NerJobSaveStatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ner/edit")
@PreAuthorize("hasRole('ROLE_PRIVILEGED_USER') OR hasRole('ROLE_ADMIN')")
public class NerJobsController {

	private final INerJobService nerJobService;

	@Autowired
	public NerJobsController(final INerJobService nerJobService) {
		this.nerJobService = nerJobService;
	}

	@GetMapping(value = "/get")
	@ResponseBody
	public NerJobDto get(@RequestParam("id")final Integer id, final User user) {

		try {
			return nerJobService.getJobById(id, user).orElse(NerJobDto.builder().build());
		} catch (NerServiceException e) {
			throw new NerWebException(e);
		}
	}

	@GetMapping(value = "/list")
	@ResponseBody
	public Page<NerJobDto> list(@RequestParam(value = "page", required = false, defaultValue = "1") final Integer page
								,final User user) {

		try {
			return nerJobService.getJobsForOwner(user, page);
		} catch (NerServiceException e) {
			throw new NerWebException(e);
		}
	}

	@PostMapping(value = "/save")
	@ResponseBody
	public NerJobSaveStatusDto save(@RequestBody NerJobDto nerJobDto, User user){
		try{
			return nerJobService.saveJob(nerJobDto, user);
		}catch (NerServiceException e){
			throw new NerWebException(e);
		}
	}

	@DeleteMapping(value = "/delete")
	@ResponseStatus(code = HttpStatus.OK)
	public void delete(@RequestParam(value = "id") final Integer jobId,
					   final User user){
		try{
			nerJobService.deleteJob(user, jobId);
		}catch (NerServiceException e){
			throw new NerWebException(e);
		}
	}
}
