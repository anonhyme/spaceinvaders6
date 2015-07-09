package org.spaceinvaders.server.dao;

import org.spaceinvaders.shared.dto.SemesterInfo;

import java.util.List;

public interface SemesterInfoDao {
    SemesterInfo getSemesterInfo(String cip, int semesterID);
    List<SemesterInfo> getSemesterInfoList(String cip);
}