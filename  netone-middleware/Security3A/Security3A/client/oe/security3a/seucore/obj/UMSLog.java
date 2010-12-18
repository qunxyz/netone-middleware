package oe.security3a.seucore.obj;

import java.sql.Timestamp;

public class UMSLog extends Entity {

	private String sourceType;

	private Long sourceId;

	private String sourceCnname;

	private String eventType;

	private String eventDescription;

	private String result;

	private String exceptionMsg;

	private String exceptionTrace;

	private Timestamp time;

	private String sourceIP;

	private String eventNote;

	public UMSLog() {
	}



	public void setSourceType(String sourceType) {

		this.sourceType = sourceType;
	}

	public void setEventType(String eventType) {

		this.eventType = eventType;
	}

	public void setTime(Timestamp time) {

		this.time = time;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public void setSourceCnname(String sourceCnname) {
		this.sourceCnname = sourceCnname;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public void setExceptionTrace(String exceptionTrace) {
		this.exceptionTrace = exceptionTrace;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	public void setSourceIP(String sourceIP) {

		this.sourceIP = sourceIP;
	}

	public void setEventNote(String eventNote) {
		this.eventNote = eventNote;
	}


	public String getSourceType() {

		return sourceType;
	}

	public String getEventType() {

		return eventType;
	}

	public Timestamp getTime() {

		return time;
	}

	public String getResult() {
		return result;
	}

	public Long getSourceId() {
		return sourceId;
	}

	public String getSourceCnname() {
		return sourceCnname;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public String getExceptionTrace() {
		return exceptionTrace;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public String getSourceIP() {

		return sourceIP;
	}

	public String getEventNote() {
		return eventNote;
	}
}
