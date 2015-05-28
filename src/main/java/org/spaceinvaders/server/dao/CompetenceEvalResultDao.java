package org.spaceinvaders.server.dao;

import org.spaceinvaders.server.entities.CompetenceEvalResultEntity;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;

import java.util.List;

public interface CompetenceEvalResultDao {
    List<CompetenceEvalResultEntity> getSemesterResultsEntities(String cip, int semesterID);
    List<CompetenceEvalResult> getSemesterResults(String cip, int semesterID);
}