package com.mobileagent.app.utilities;

public class MessageEvent {

	private long id;
	private String attribute;
	private String value1;
	private String value2;
	private String mesage;
	private String action;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public String getMesage() {
		return mesage;
	}
	public void setMesage(String mesage) {
		this.mesage = mesage;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	@Override
	public String toString() {
		return "MessageEvent [attribute=" + attribute + ", value1=" + value1
				+ ", value2=" + value2 + ", mesage=" + mesage + ", action="
				+ action + "]";
	}
	
	
}
