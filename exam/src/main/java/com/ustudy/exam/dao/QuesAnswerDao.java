package com.ustudy.exam.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.QuesAnswer;

@MapperScan
public interface QuesAnswerDao {

	List<QuesAnswer> getQuesAnswers(Long egsId);
	
	List<QuesAnswer> getQuestions(@Param("egsIds")List<Long> egsIds);
	
	QuesAnswer getQuesAnswer(@Param("egsId")Long egsId, @Param("quesId")Long quesId);
	
	List<QuesAnswer> getQuesAnswersByIds(String egsIds);
	
	boolean deleteQuesAnswers(Long egsId);
	
	boolean deleteQuesAnswerByIds(@Param("egsId")Long egsId, @Param("quesAnswerIds")List<Integer> quesAnswerIds);
	
	boolean insertQuesAnswers(List<QuesAnswer> quesAnswers);
	
	boolean initQuesAnswers(List<QuesAnswer> quesAnswers);
	
	boolean updateQuesAnswer(QuesAnswer quesAnswer);
	
	List<Map<String, Object>> getObjectQuestions(Long egsId);
	
}
