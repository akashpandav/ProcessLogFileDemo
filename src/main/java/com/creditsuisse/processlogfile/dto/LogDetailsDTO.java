package com.creditsuisse.processlogfile.dto;


public class LogDetailsDTO {
	private String id;
	
	private String state;
	
	private String type;

	private String host;

	private long timeStamp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		return "LogDetailsDTO [id=" + id + ", state=" + state + ", type=" + type + ", host=" + host + ", timeStamp="
				+ timeStamp + "]";
	}

}
