package com.ustudy.exam.model.score;

import java.io.Serializable;

public class SubChildScore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6590093362364537127L;
	
	private int subId = 0;
	private String subName = null;
	private float score = 0;
	private int rank = 0;
	
	public SubChildScore() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SubChildScore(int subId, String subName, float score, int rank) {
		super();
		this.subId = subId;
		this.subName = subName;
		this.score = score;
		this.rank = rank;
	}

	public int getRank() {
		return rank;
	}
	
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public float getScore() {
		return score;
	}
	
	public void setScore(float score) {
		this.score = score;
	}
	
	public int getSubId() {
		return subId;
	}
	
	public void setSubId(int subId) {
		this.subId = subId;
	}
	
	public String getSubName() {
		return subName;
	}
	
	public void setSubName(String subName) {
		this.subName = subName;
	}
	
	@Override
	public String toString() {
		return "SubChildScore [rank=" + rank + ", score=" + score + ", subId=" + subId + ", subName=" + subName + "]";
	}

}
