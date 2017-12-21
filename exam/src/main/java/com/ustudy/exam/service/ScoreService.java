package com.ustudy.exam.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface ScoreService {

	boolean recalculateQuestionScore(Long egsId, Integer quesno, String answer) throws Exception;
	
	boolean recalculateQuestionScore(Long egsId) throws Exception;
	
	boolean publishExamScore(Long examId, Boolean release) throws Exception;
	
	JSONArray getStudentSubjects(Long examId, Long schId, Long gradeId, Long classId, Long subjectId, String branch, String text) throws Exception;
	
	JSONObject getStudentScores(Long stuId, Long examId) throws Exception;
	
}
