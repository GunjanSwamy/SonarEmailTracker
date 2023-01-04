package com.ub.sonar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ub.sonar.facade.PixelFacade;
import com.ub.sonar.service.NudgeServiceImpl;

@RestController
@RequestMapping("/nudge")
public class NudgeController {

	@Autowired
	PixelFacade pixelFacade;

	@Autowired
	NudgeServiceImpl nudgeService;

	@RequestMapping(value = "/{uuid}/{time}", method = RequestMethod.GET)
	public String updateReadRecepts(@PathVariable String uuid, @PathVariable String time) {
 
		if (String.valueOf(time).equals("delete")) {
			nudgeService.removeNudgeTriggerTime(uuid);
			return "You have removed nudge for this email, you can close this tab";
		}
		return nudgeService.updateNudgeTriggerTime(Integer.parseInt(time), uuid);
		 
	}

}
