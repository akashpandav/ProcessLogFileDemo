package com.creditsuisse.processlogfile.service;

import java.sql.Date;
import java.util.List;

import com.creditsuisse.processlogfile.dto.LogDetailsDTO;

public class LogDataCreator extends  Thread {
	
	List<LogDetailsDTO> logDetailsDTOs;
	
	int id;
	

	public LogDataCreator(List<LogDetailsDTO> logDetailsDTOs,int id) {
		super();
		this.logDetailsDTOs = logDetailsDTOs;
		this.id = id;
	}



	@Override
	public void run() {
		LogDetailsDTO logDetailsDTO = new LogDetailsDTO();
		logDetailsDTO.setId("Testlog"+id);
		logDetailsDTO.setState("STARTED");
		logDetailsDTO.setHost("LocalHost");
		logDetailsDTO.setType("Application_log");
		logDetailsDTO.setTimeStamp(System.currentTimeMillis());
		logDetailsDTOs.add(logDetailsDTO);
		for(int i= 0; i<=5;i++) {
		System.out.println(i);
		}
		
		try { 
			if(id%4 ==0) {
			
				Thread.sleep(40);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LogDetailsDTO logDetailsDTOFinished = new LogDetailsDTO();
		logDetailsDTOFinished.setId("Testlog"+id);
		logDetailsDTOFinished.setState("FINISHED");
		logDetailsDTOFinished.setHost("LocalHost");
		logDetailsDTOFinished.setType("Application_log");
		logDetailsDTOFinished.setTimeStamp(System.currentTimeMillis());
		logDetailsDTOs.add(logDetailsDTOFinished);
		
		// TODO Auto-generated method stub
		
	}
	

}
