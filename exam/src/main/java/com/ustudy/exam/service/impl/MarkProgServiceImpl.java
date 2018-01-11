package com.ustudy.exam.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ustudy.exam.mapper.MarkProgressMapper;
import com.ustudy.exam.model.statics.EgsMarkProgress;
import com.ustudy.exam.model.statics.EgsMeta;
import com.ustudy.exam.model.statics.ExamMarkProgress;
import com.ustudy.exam.model.statics.QuesMarkMetrics;
import com.ustudy.exam.service.MarkProgService;

@Service
public class MarkProgServiceImpl implements MarkProgService {

	private static final Logger logger = LogManager.getLogger(MarkProgServiceImpl.class);
	
	@Autowired
	private MarkProgressMapper mpM;
	
	@Override
	public List<ExamMarkProgress> getExamMarkProg(String sid) {
		
		List<EgsMeta> exMetaL = mpM.getExamMetaInfo(sid);
		
		List<ExamMarkProgress> expL = null;
		// need to convert EgsMeta to ExamMarkProgress
		if (exMetaL != null && !exMetaL.isEmpty()) {
			expL = getExMarkProg(exMetaL);
		}
		else {
			expL = new ArrayList<ExamMarkProgress>();
		}
		
		return expL;
	}

	@Override
	public List<QuesMarkMetrics> getEgsMarkProg(int eid, int egsid) {
		
		List<QuesMarkMetrics> qmt = mpM.getQuesMarkMetricsByEgsId(eid, egsid);
		
		logger.debug("getEgsMarkProg(), QuesMarkMetrics for egsid->" + egsid + qmt.toString());
		return qmt;
	}

	private List<ExamMarkProgress> getExMarkProg(List<EgsMeta> emL) {
		
		HashMap<Integer, ExamMarkProgress> exmM = new HashMap<Integer, ExamMarkProgress>();
		
		for (EgsMeta em: emL) {
			
			EgsMarkProgress egs = new EgsMarkProgress(em.getEgsId(), em.getGradeId(), em.getGradeName(), 
					em.getSubId(), em.getSubName());
			List<QuesMarkMetrics> qmm = mpM.getQuesMarkMetricsByEgsId(em.getExamId(), em.getEgsId());
			egs.setMetrics(qmm);
			
			if (exmM.containsKey(Integer.valueOf(em.getExamId()))) {
				ExamMarkProgress emp = exmM.get(em.getExamId());
				
				emp.getEgs().add(egs);
			}
			else {
				ExamMarkProgress emp = new ExamMarkProgress(em.getExamId(), em.getExamName(), 
						em.getSchoolId(), em.getSchoolName());
				List<EgsMarkProgress> egmL = new ArrayList<EgsMarkProgress>();
				egmL.add(egs);
				emp.setEgs(egmL);
				exmM.put(Integer.valueOf(em.getExamId()), emp);
			}
			
		}
		
		logger.debug("getExMarkProg(), " + exmM.values());
		
		return new ArrayList<ExamMarkProgress>(exmM.values());
	}
	
}
