package com.creditsuisse.processlogfile.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.creditsuisse.processlogfile.entity.EventEntity;

@Repository
public class EventRepositoryImpl {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void saveAllEvents(List<EventEntity> eventEntities) {
		
		String insertQuery= "Insert into event_entity ( event_id , event_duration , host , type, alert ) VALUES (?,?,?,?,?)";
		
		Session session = entityManager.unwrap(Session.class);
		
		session.doWork(connection ->{
			try(PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
				
				for (EventEntity eventEntity : eventEntities) {
					preparedStatement.setString(1, eventEntity.getEventId());
					preparedStatement.setLong(2, eventEntity.getEventDuration());
					preparedStatement.setString(3, eventEntity.getHost());
					preparedStatement.setString(4, eventEntity.getType());
					preparedStatement.setBoolean(5, eventEntity.getAlert());
					preparedStatement.addBatch();
				}
             preparedStatement.executeBatch();				
				
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		});
		
		
	}

}
