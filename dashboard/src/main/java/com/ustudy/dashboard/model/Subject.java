package com.ustudy.dashboard.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Subject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -375085025372724305L;
	
	@JsonProperty("subject")
	private String subName = null;
	
    @JsonProperty("id")
	private String subId = null;

	
	public Subject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Subject(String subName, String subId) {
		super();
		this.subName = subName;
		this.subId = subId;
	}


	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	@Override
	public String toString() {
		return "Subject [subName=" + subName + ", subId=" + subId + "]";
	}

	
}