package org.spaceinvaders.server.dao;

import org.spaceinvaders.server.entities.*;
import org.spaceinvaders.shared.dto.CompetenceEvalResultDto;

import java.util.List;

public interface CompetenceEvalResultDao {
    List<CompetenceEvalResultEntity> getSemesterResultsEntities(String cip, int semesterID);

    List<CompetenceEvalResultDto> getSemesterResults(String cip, int semesterID);
}
