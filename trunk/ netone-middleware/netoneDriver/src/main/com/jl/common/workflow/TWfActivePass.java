package com.jl.common.workflow;

/**
 * 旧的活动节点，通过是用来做回退逻辑时使用到的
 * 
 * @author chenjx <br>
 *         email:oesee@139.com, tel:15860836998
 */
public final class TWfActivePass extends TWfActive {
	/**
	 * 曾经的抄阅者
	 */
	String[] reader;
	/**
	 * 曾经的抄送者
	 */
	String[] assistant;
	/**
	 * 曾经的参与者
	 */
	String[] participantOld;
	/**
	 * 抄送者是否同步
	 */
	boolean assistantSync;

	public String[] getReader() {
		return reader;
	}

	public void setReader(String[] reader) {
		this.reader = reader;
	}

	public String[] getAssistant() {
		return assistant;
	}

	public void participantold(String[] assistant) {
		this.assistant = assistant;
	}

	public String[] getParticipantOld() {
		return participantOld;
	}

	public void setParticipantOld(String[] participantOld) {
		this.participantOld = participantOld;
	}

	public boolean isAssistantSync() {
		return assistantSync;
	}

	public void setAssistantSync(boolean assistantSync) {
		this.assistantSync = assistantSync;
	}

	public void setAssistant(String[] assistant) {
		this.assistant = assistant;
	}

}
