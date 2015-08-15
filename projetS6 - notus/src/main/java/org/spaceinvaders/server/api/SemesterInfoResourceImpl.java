package org.spaceinvaders.server.api;

import com.google.inject.Inject;

import org.spaceinvaders.server.cas.UserSession;
import org.spaceinvaders.server.dao.SemesterInfoDao;
import org.spaceinvaders.shared.api.SemesterInfoResource;
import org.spaceinvaders.shared.dto.SemesterInfo;

import java.util.List;

public class SemesterInfoResourceImpl implements SemesterInfoResource {
    @Inject
    SemesterInfoDao semesterInfoDao;

    @Inject
    public UserSession userSession;

    @Override
    public SemesterInfo get(int semesterID) {
        return semesterInfoDao.getSemesterInfo(userSession.getUserId(), semesterID);
    }

    @Override
    public List<SemesterInfo> getAll() {
        return semesterInfoDao.getSemesterInfoList(userSession.getUserId());
    }
}
