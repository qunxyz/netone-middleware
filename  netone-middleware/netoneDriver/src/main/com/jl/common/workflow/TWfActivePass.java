package com.jl.common.workflow;

/**
 * �ɵĻ�ڵ㣬ͨ���������������߼�ʱʹ�õ���
 * 
 * @author chenjx <br>
 *         email:oesee@139.com, tel:15860836998
 */
public final class TWfActivePass extends TWfActive {
	/**
	 * �����ĳ�����
	 */
	String[] reader;
	/**
	 * �����ĳ�����
	 */
	String[] assistant;
	/**
	 * �����Ĳ�����
	 */
	String[] participantOld;
	/**
	 * �������Ƿ�ͬ��
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
