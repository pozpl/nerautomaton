package com.pozpl.nerannotator.ner.management.text;

import com.pozpl.nerannotator.shared.exceptions.NerWebException;
import com.pozpl.nerannotator.auth.dao.model.User;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.ner.management.text.IJobTextEditService;
import com.pozpl.nerannotator.ner.management.text.JobTextDto;
import com.pozpl.nerannotator.ner.management.text.JobTextEditStatusDto;
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

	@GetMapping(value = "/get")
	@ResponseBody
	public JobTextDto get(@RequestParam("id") final Integer id, final User user) {

		try {
			return jobTextEditService.getById(id, user);
		} catch (NerServiceException e) {
			throw new NerWebException(e);
		}
	}


	@PostMapping(value = "/save")
	@ResponseBody
	public JobTextEditStatusDto save(@RequestBody final JobTextDto jobTextDto, final User user){
		try{
			return jobTextEditService.save(jobTextDto, user);
		}catch (NerServiceException e){
			throw new NerWebException(e);
		}
	}

	@DeleteMapping(value = "/delete")
	@ResponseStatus(code = HttpStatus.OK)
	public void delete(@RequestParam(value = "id") final Integer jobTextId, final User user){
		try{
			jobTextEditService.deleteJobText(jobTextId, user);
		}catch (NerServiceException e){
			throw new NerWebException(e);
		}
	}

}