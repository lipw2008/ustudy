package com.ustudy.exam.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.UResp;
import com.ustudy.exam.model.statics.ScoreClass;
import com.ustudy.exam.service.ScoreService;

@RestController
@RequestMapping(value = "/score")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ScoreController {

	private static final Logger logger = LogManager.getLogger(ScoreController.class);

	@Autowired
	private ScoreService service;
	
	@RequestMapping(value = "/recalculate/{egsId}/{quesno}/{answer}", method = RequestMethod.POST)
    public Map recalculateQuestionScore(@PathVariable Long egsId, @PathVariable Integer quesno, @PathVariable String answer, HttpServletRequest request,
            HttpServletResponse response) {

        logger.debug("recalculateQuestionScore().");
        logger.debug("egsId: " + egsId + ",quesno=" + quesno + ",answer=" + answer);

        Map result = new HashMap<>();

        try {
            if (service.recalculateQuestionScore(egsId, quesno, answer)) {
                result.put("success", true);
            } else {
                result.put("success", false);
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping(value = "/recalculate/{egsId}", method = RequestMethod.POST)
    public Map recalculateEgsScore(@PathVariable Long egsId, HttpServletRequest request,
            HttpServletResponse response) {

        logger.debug("recalculateEgsScore(), egs->" + egsId);

        Map result = new HashMap<>();

        try {
            if (service.recalculateQuestionScore(egsId)) {
                result.put("success", true);
            } else {
                result.put("success", false);
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
            logger.error("recalculateEgsScore(), failed with->" + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping(value = "/publish/{examId}/{release}", method = RequestMethod.POST)
    public Map publishExamScore(@PathVariable Long examId, @PathVariable Boolean release) {

        logger.debug("publishExamScore(examId:"+examId+",release:"+release+").");

        Map result = new HashMap<>();

        try {
        	if (release) {
        		if (service.publishExamScore(examId, release)) {
        			result.put("success", true);
        			result.put("message", "考试成绩发布成功");
        		} else {
        			result.put("success", false);
        			result.put("message", "考试成绩发布失败");
        		}
        	} else {
        		if (service.publishExamScore(examId, release)) {
        			result.put("success", true);
        			result.put("message", "考试成绩取消发布成功");
        		} else {
        			result.put("success", false);
        			result.put("message", "考试成绩取消发布失败");
        		}
			}
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
    
    /**
     * 
     * getStudentSubjects[统计考生成绩]
     * 创建人:  dulei
     * 创建时间: 2017年12月20日 下午10:48:54
     *
     * @Title: getStudentSubjects
     * @param examId 考试ID
     * @param schId 学校ID
     * @param gradeId 年级ID
     * @param classId 班级ID
     * @param subjectId 科目ID
     * @param branch 分科:(文科:art,理科:sci,不分科：none)
     * @param text 考生姓名或考号
     * @return
     */
    @RequestMapping(value = "/students/subjects/{examId}")
    public Map getStudentSubjects(@PathVariable Long examId,
            @RequestParam(required=false,defaultValue="0") Long schId, 
            @RequestParam(required=false,defaultValue="0") Long gradeId, 
            @RequestParam(required=false,defaultValue="0") Long classId, 
            @RequestParam(required=false,defaultValue="0") Long subjectId, 
            @RequestParam(required=false,defaultValue="") String branch, 
            @RequestParam(required=false,defaultValue="") String text) {
        
        logger.info("getStudentSubjects(examId:"+examId+",schId:"+schId+",gradeId:"+gradeId+",classId:"+classId+",subjectId:"+subjectId+",branch:"+branch+",text:"+text+").");
        
        Map result = new HashMap<>();
        
        try {
            result.put("data", service.getStudentSubjects(examId, schId, gradeId, classId, subjectId, branch, text));
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
            e.printStackTrace();
        }
        
        return result;
    }
    
    /**
     * 
     * getStudentScores[考生的分明细]
     * 创建人:  dulei
     * 创建时间: 2017年12月21日 下午9:00:15
     *
     * @Title: getStudentScores
     * @param stuId 考生ID
     * @param examId 考试ID
     * @return
     */
    @RequestMapping(value = "/student/scores/{stuId}/{examId}")
    public Map getStudentScores(@PathVariable Long stuId, @PathVariable Long examId) {
    	
    	logger.info("getStudentScores(stuId:"+stuId+",examId:"+examId+")");
    	
    	Map result = new HashMap<>();
    	
    	try {
            result.put("data", service.getStudentScores(stuId, examId));
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
            e.printStackTrace();
        }
    	
    	return result;
    }

    @RequestMapping(value = "/cls/{eid}/{gid}/", method = RequestMethod.GET)
    public UResp getClsScore(@PathVariable("eid") @Valid int eid, @PathVariable("gid") @Valid int gid, 
    		HttpServletResponse resp) {
    	logger.debug("getClsScore(), retrieve class scores for exam->" + eid + ", gid->" + gid);
    	UResp res = new UResp();
    	
    	// if gid <=0 indicates retrieve scores for all grades
    	if (eid < 0) {
    		logger.error("getClsScore(), parameter invalid");
    		res.setMessage("getClsScore(), parameter invalid");
    		return res;
    	}
    	
    	try {
    		List<ScoreClass> scL= service.getClsScores(eid, gid);
    		res.setData(scL);
    		res.setRet(true);
    		res.setMessage("retrieve class score information succeeded.");
    	} catch (Exception e) {
    		logger.error("getClsScore(), failed with exception->" + e.getMessage());
    		resp.setStatus(500);
    		res.setMessage("retrieve class scores failed");
    	}
    	
    	return res;
    }
    
    @RequestMapping(value = "/collect/finished/{egsid}", method = RequestMethod.GET)
    public UResp isScoreCalculated(@PathVariable("egsid") int egsid, HttpServletResponse resp) {
    	UResp res = new UResp();
    	
    	try {
    		res.setData(service.isScoreCalculated(egsid));
    		res.setRet(true);
    		logger.debug("isScoreCalculated(), score collected status for esgid->" + egsid 
    				+ " " + res.getData());
    	} catch (Exception e) {
    		logger.error("isScoreCalculated(), failed with->" + e.getMessage());
    		resp.setStatus(500);
    		res.setMessage("Failed with ->" + e.getMessage());
    	}
    	return res;
    }
}
