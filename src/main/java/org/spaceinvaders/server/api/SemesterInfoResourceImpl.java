package org.spaceinvaders.server.api;

import com.google.inject.Inject;

import org.spaceinvaders.server.cas.UserSessionImpl;
import org.spaceinvaders.server.dao.SemesterInfoDao;
import org.spaceinvaders.shared.api.SemesterInfoResource;
import org.spaceinvaders.shared.dto.SemesterInfo;

import java.util.ArrayList;
import java.util.List;

public class SemesterInfoResourceImpl implements SemesterInfoResource {
    @Inject
    SemesterInfoDao semesterInfoDao;

    @Inject
    public UserSessionImpl userSession;

    @Override
    public SemesterInfo get(int semesterID) {
        return semesterInfoDao.getSemesterInfo(userSession.getUserId(), semesterID);
    }

    @Override
    public List<SemesterInfo> getAll() {
        List<SemesterInfo> semesterInfoList = new ArrayList<>();

//        SemesterInfo semesterInfo = new SemesterInfo();
//        semesterInfo.setId(0);
//        semesterInfo.setLabel("Session " + 1);
//        semesterInfoList.add(semesterInfo);
//        semesterInfoList.add(semesterInfo);
//        semesterInfoList.add(semesterInfo);
        return semesterInfoDao.getSemesterInfoList("boua2354");
    }
}
