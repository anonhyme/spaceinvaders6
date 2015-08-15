package org.spaceinvaders.server.cas;

import com.google.inject.Inject;
import com.google.inject.Provider;

import org.spaceinvaders.shared.dto.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserSessionOffline implements UserSession {

    private Provider<HttpServletRequest> provider = null;
    private HttpSession session = null;

    @Inject
    public UserSessionOffline(Provider<HttpServletRequest> requestProvider) {
        provider = requestProvider;
    }

    public UserSessionOffline(HttpServletRequest request) {
        if (request.isRequestedSessionIdValid() && request.isRequestedSessionIdFromCookie()) {
            session = request.getSession(false);
        }
    }

    public UserSessionOffline(HttpSession session) {
        this.session = session;
    }

    private HttpSession getSession() {
        if (session != null) {
            return session;
        } else if (provider.get().isRequestedSessionIdValid()) {
            return provider.get().getSession(false);
        } else {
            return null;
        }
    }

    public boolean isValid() {
        return getSession() != null;
    }

    public UserInfo getUserInfo() {

        String cip = "abcd1234";
        String firstName = "anonym";
        String lastName = "Us";
        String email = "anonym@null.xyz";

        UserInfo userInfo = new UserInfo();
        userInfo.setCip(cip);
        userInfo.setFirstName(firstName);
        userInfo.setLastName(lastName);
        userInfo.setEmail(email);

        return userInfo;
    }

    public String getUserId() {
        return "abcd1234";
    }
}