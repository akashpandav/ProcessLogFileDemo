
package com.creditsuisse.processlogfile.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.creditsuisse.processlogfile.dto.LogDetailsDTO;
import com.creditsuisse.processlogfile.entity.EventEntity;
import com.creditsuisse.processlogfile.service.EventService;
import com.creditsuisse.processlogfile.service.LogDataCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@Validated
@RequestMapping("/api")
public class EventController {


	@Autowired
	private EventService eventService;

	@Autowired
	private ObjectMapper objectMapper;
	

	private static final Logger LOGGER=LoggerFactory.getLogger(EventController.class);
	
	/**
	 * This method will read and parse the file content  present at path provided as 
	 * a input to rest endpoint as a RequestParm and send to service layer for processing
	 * Below is the example url: http://localhost:8080/allevents?logfilePath=E://Projects//ProcessLogFileDemo//log.txt
	 * 
	 * @param logfilePath
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 * @throws Exception
	 */

	@GetMapping("/processlogfile")
	public ResponseEntity<Map<String, EventEntity>> processlogfile(@RequestParam("logfilePath") @NotBlank String logfilePath) throws JsonMappingException, JsonProcessingException, Exception {

		List<LogDetailsDTO> logDetailsDTOs = objectMapper
				.readValue(readFileAsString(logfilePath), new TypeReference<List<LogDetailsDTO>>(){});

		LOGGER.info("Completed the reading and parsing of the file {} and strated processing in batch of 1000 events", logfilePath);

		return new ResponseEntity<>(eventService.saveLogDetails(logDetailsDTOs),HttpStatus.CREATED);
	}


	public  String readFileAsString(String fileName)throws Exception
	{
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		return data;
	}
	
	/**
	 * This method will method will create the log events and also log file in current project directory
	 * Below is the example url: http://localhost:8080/createlogfile/10000
	 * 
	 * @param logfilePath
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 * @throws Exception
	 */
	@GetMapping("/createlogfile/{numberOfEvents}")
	public List<LogDetailsDTO> createLogFile(@PathVariable("numberOfEvents") int numberOfEvents) throws InterruptedException {

		List<LogDetailsDTO> logDetailsDTOs =new ArrayList<>();
		List<LogDataCreator> logDataCreators =new ArrayList<>();

		for(int i=1;i<= numberOfEvents;i++) {
			LogDataCreator logDataCreator = new LogDataCreator(logDetailsDTOs, i);
			logDataCreator.start();
			logDataCreators.add(logDataCreator);
		}

		logDataCreators.stream().forEach(logDataCreator -> {try {
			logDataCreator.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});

		createFile(logDetailsDTOs);
		return logDetailsDTOs;
	}

   
	private void createFile(List<LogDetailsDTO> logDetailsDTOs) {
		// TODO Auto-generated method stub
		try {
			String str = objectMapper.writeValueAsString(logDetailsDTOs);

			BufferedWriter writer = new BufferedWriter(new FileWriter("log.txt"));

			writer.write(str);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}



}
