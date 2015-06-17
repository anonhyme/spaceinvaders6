package org.spaceinvaders.server.api;

import com.google.inject.Inject;
import org.spaceinvaders.server.cas.UserSessionImpl;
import org.spaceinvaders.server.dao.SemesterInfoDao;
import org.spaceinvaders.shared.api.SemesterInfoResource;
import org.spaceinvaders.shared.dto.SemesterInfo;

public class SemesterInfoResourceImpl implements SemesterInfoResource {

    @Inject
    SemesterInfoDao semesterInfoDao;

    @Inject
    public UserSessionImpl userSession;

    @Override
    public SemesterInfo get(int semesterID) {
        return semesterInfoDao.getSemesterInfo(userSession.getUserId(), semesterID);
    }
}