package com.example.logs_aws.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class LogsController {

	@GetMapping("/course")
	public ResponseEntity<?> getCourses(@RequestParam String subject){
		log.info("in getCourse");
		String output = null;
		if(subject==null || subject=="") {
			log.error("No subject passed");
		}
		List<String> course = List.of("English","Hindi","French","German","Spanish");
		
		if(course.contains(subject)) {
			output = getFaculty(subject);
		}
		else {
			log.error("Wrong course passed");
		}
		return ResponseEntity.ok(output);
	}
	
	private String getFaculty(String subject) {
		String result = null;
		switch(subject) {
		case "English": 
			result="Mrs Smitha teaches english";
			log.info(result);
		break;
		case "Hindi" :
			result="Mrs Rekha teaches hindi";
			log.info(result);
		break;
		default:
			result="No matching subject found";
	        log.info(result);
	        break;
		}
		return result;
	}
	
}
