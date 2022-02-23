package com.creditsuisse.processlogfile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.creditsuisse.processlogfile.entity.EventEntity;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
	
}