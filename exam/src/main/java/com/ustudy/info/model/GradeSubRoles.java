package com.ustudy.info.model;

import java.io.Serializable;
import java.util.List;

import com.ustudy.Item;

/**
 * @author jared
 * 
 * Grade, related subjects and roles information for teacher
 * 
 * Before creating teacher, need construct information from school and send to frontend
 * 
 */
public class GradeSubRoles implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4511281491452584288L;

	private List<SchGradeSub> grades = null;
	
	private List<Item> roles = null;

	public GradeSubRoles() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GradeSubRoles(List<SchGradeSub> grades, List<Item> roles) {
		super();
		this.grades = grades;
		this.roles = roles;
	}

	public List<SchGradeSub> getGrades() {
		return grades;
	}

	public void setGrades(List<SchGradeSub> grades) {
		this.grades = grades;
	}

	public List<Item> getRoles() {
		return roles;
	}

	public void setRoles(List<Item> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "GradeSubRoles [grades=" + grades + ", roles=" + roles + "]";
	}
	
}
