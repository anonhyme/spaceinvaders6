package org.spaceinvaders.server.api;

import com.google.inject.Inject;
import org.spaceinvaders.server.cas.UserSessionImpl;
import org.spaceinvaders.shared.api.UserInfoResource;
import org.spaceinvaders.shared.dispatch.UserInfo;

public class UserInfoResourceImpl implements UserInfoResource {
    @Inject
    public UserSessionImpl userSession;

    @Override
    public UserInfo get() {
        return userSession.getUserInfo();
    }
}
