package com.pozpl.nerannotator.controllers.ner;

import com.pozpl.nerannotator.controllers.exceptions.NerWebException;
import com.pozpl.nerannotator.persistence.model.User;
import com.pozpl.nerannotator.service.exceptions.NerServiceException;
import com.pozpl.nerannotator.service.ner.text.IJobTextEditService;
import com.pozpl.nerannotator.service.ner.text.JobTextDto;
import com.pozpl.nerannotator.service.ner.text.JobTextEditStatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Expose interface for editing Ner text to the user
 */
@Controller
@RequestMapping("/ner/text/edit/")
public class NerTextEditController {

	private IJobTextEditService jobTextEditService;

	@Autowired
	public NerTextEditController(IJobTextEditService jobTextEditService) {
		this.jobTextEditService = jobTextEditService;
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public JobTextDto get(@RequestParam("id") final Integer id, final User user) {

		try {
			return jobTextEditService.getById(id, user);
		} catch (NerServiceException e) {
			throw new NerWebException(e);
		}
	}


	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public JobTextEditStatusDto save(@RequestBody final JobTextDto jobTextDto, final User user){
		try{
			return jobTextEditService.save(jobTextDto, user);
		}catch (NerServiceException e){
			throw new NerWebException(e);
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	@ResponseStatus(code = HttpStatus.OK)
	public void delete(@RequestParam(value = "id") final Integer jobTextId, final User user){
		try{
			jobTextEditService.deleteJobText(jobTextId, user);
		}catch (NerServiceException e){
			throw new NerWebException(e);
		}
	}

}
