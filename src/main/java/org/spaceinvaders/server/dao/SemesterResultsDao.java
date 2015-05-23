package org.spaceinvaders.server.dao;

import org.spaceinvaders.server.entities.*;
import org.spaceinvaders.shared.dto.EvaluationResultsDto;

import java.util.List;

public interface SemesterResultsDao {
    List<EvaluationResultsEntity> getSemesterResultsEntities(String cip, int semesterID);
    List<EvaluationResultsDto> getSemesterResults(String cip, int semesterID);
}
