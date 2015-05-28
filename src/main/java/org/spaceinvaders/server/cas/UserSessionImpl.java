package org.spaceinvaders.server.cas;

import com.google.appengine.api.users.UserService;
import com.google.inject.Inject;
import com.google.inject.Provider;

import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UserSessionImpl implements UserSession {

    private Provider<HttpServletRequest> provider = null;
    private HttpSession session = null;

    @Inject
    public UserSessionImpl(Provider<HttpServletRequest> requestProvider) {
        provider = requestProvider;
    }

    public UserSessionImpl(HttpServletRequest request) {
        if (request.isRequestedSessionIdValid() && request.isRequestedSessionIdFromCookie()) {
            session = request.getSession(false);
        }
    }

    public UserSessionImpl(HttpSession session) {
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

    public String getUserId() {
        HttpSession session = getSession();
        String cip = "";

        try {
            Assertion assertion = (Assertion) session.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
            cip = assertion.getPrincipal().getName();
        } catch (NullPointerException ex) {
            // check your web.xml
            Logger.getLogger(UserSessionImpl.class.getName()).log(Level.SEVERE, "Session or CAS assertion is null, verify your web.xml");
        }

        return cip;
    }
}