package org.spaceinvaders.server.api;

import com.google.inject.Inject;

import org.spaceinvaders.server.cas.UserSession;
import org.spaceinvaders.shared.api.UserInfoResource;
import org.spaceinvaders.shared.dto.UserInfo;

public class UserInfoResourceImpl implements UserInfoResource {
    @Inject
    public UserSession userSession;

    @Override
    public UserInfo get() {
        return userSession.getUserInfo();
    }
}
