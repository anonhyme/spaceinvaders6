package org.spaceinvaders.server.cas;

import org.spaceinvaders.shared.dto.UserInfo;

/**
 * Created with IntelliJ IDEA Project: notus on 8/14/2015
 *
 * @author antoine
 */
public interface UserSession {
    boolean isValid();

    UserInfo getUserInfo();

    String getUserId();
}
