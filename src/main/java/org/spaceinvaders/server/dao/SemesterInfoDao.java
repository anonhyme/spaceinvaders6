package org.spaceinvaders.server.dao;

import org.spaceinvaders.server.entities.CompetenceEntity;
import org.spaceinvaders.server.entities.EvaluationEntity;
import org.spaceinvaders.shared.dto.SemesterInfo;

import java.util.List;

public interface SemesterInfoDao {
    SemesterInfo getSemesterInfo(String cip, int semesterID);

    //TODO for test use with the mock. Return a list of SemesterInfo for the dropdown semester menu.
    List<SemesterInfo> getSemesterInfoList(String cip);
}