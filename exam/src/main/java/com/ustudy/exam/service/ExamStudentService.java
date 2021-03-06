package com.ustudy.exam.service;

import java.util.Map;

import net.sf.json.JSONArray;

public interface ExamStudentService {

	JSONArray getStudentInfoByExamGrade(Long examId, Long gradeId);
	
	Map<String, Object> getExamStudents(Long examId, Long gradeId, Long classId, String studentName);
	
	Map<String, Object> getMissExamStudents(Long egsId, Long gradeId, Long classId, String studentName);
	
}
