package com.creditsuisse.processlogfile.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class EventEntity {
    
    @Id
    @Column
    private String eventId;
    
    @Column
    private long eventDuration;
    
    @Column
    private String type;
    
    @Column
    private String host;
    
    @Column
    private Boolean alert;
    
    public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public long getEventDuration() {
		return eventDuration;
	}

	public void setEventDuration(long eventDuration) {
		this.eventDuration = eventDuration;
	}

	public String getType() {
		return type;
	}

	

	public void setType(String type) {
		this.type = type;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Boolean getAlert() {
		return alert;
	}

	public void setAlert(Boolean alert) {
		this.alert = alert;
	}

	
	@Override
	public String toString() {
		return "EventEntity [eventId=" + eventId + ", eventDuration=" + eventDuration + ", type=" + type + ", host="
				+ host + ", alert=" + alert + "]";
	}
}