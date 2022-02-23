package com.creditsuisse.processlogfile.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditsuisse.processlogfile.controller.EventController;
import com.creditsuisse.processlogfile.dto.LogDetailsDTO;
import com.creditsuisse.processlogfile.entity.EventEntity;
import com.creditsuisse.processlogfile.repository.EventRepositoryImpl;

@Service
public class EventService {

	@Autowired
	private EventRepositoryImpl repository;
	private static final Logger LOGGER=LoggerFactory.getLogger(EventController.class);

	public Map<String, EventEntity> saveLogDetails(List<LogDetailsDTO> logDetailsDTOs) {

		List<EventEntity> eventEntities = new ArrayList<>();

		List<Thread> threads = new ArrayList<>();
		Map<String, EventEntity> eventEntityByIdMap = new HashMap<>();
		logDetailsDTOs.stream().forEach(logDetailsDto ->{

			EventEntity eventEntity  = eventEntityByIdMap.getOrDefault(logDetailsDto.getId(), new EventEntity());
			eventEntity.setEventId(logDetailsDto.getId());
			eventEntity.setHost(logDetailsDto.getHost());
			eventEntity.setType(logDetailsDto.getType());
			if("STARTED".equalsIgnoreCase(logDetailsDto.getState())) {
				eventEntity.setEventDuration(logDetailsDto.getTimeStamp());
				eventEntityByIdMap.put(logDetailsDto.getId(), eventEntity);
			}else {
				long eventDuration = logDetailsDto.getTimeStamp() - eventEntity.getEventDuration();
				eventEntity.setEventDuration(eventDuration);
				if(eventDuration > 4) {
					eventEntity.setAlert(true);
				}else {
					eventEntity.setAlert(false);
				}
				eventEntityByIdMap.put(logDetailsDto.getId(), eventEntity);
				eventEntities.add(eventEntity);
				if(eventEntities.size() > 1000) {

					List<EventEntity> eventEntitiesTemp = new ArrayList<>(eventEntities);
					eventEntities.clear();
					Thread saveThread =new Thread(new Runnable() {
						public void run() {
							repository.saveAllEvents(eventEntitiesTemp);	
						}
					});

					saveThread.start();
					threads.add(saveThread);
				}
			}
			repository.saveAllEvents(eventEntities);	
			LOGGER.info("Successfully persisted the all the events in file");

			threads.parallelStream().forEach(thread -> {try {
				thread.join();
			} catch (InterruptedException e) {
				LOGGER.info("Exception occured while processing {}", e.getStackTrace().toString());
			}});
		} );


		LOGGER.info("Successfully persisted the batch of {} events", logDetailsDTOs.size());
		return eventEntityByIdMap;
	}

}
//http://localhost:8080/allevents?logfilePath=E://Projects//ProcessLogFileDemo//log.txt