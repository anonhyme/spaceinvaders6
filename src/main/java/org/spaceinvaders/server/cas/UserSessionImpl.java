package org.spaceinvaders.server.cas;

import com.google.inject.Inject;
import com.google.inject.Provider;

import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.spaceinvaders.shared.dto.UserInfo;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserSessionImpl implements UserSession {

    private Provider<HttpServletRequest> provider = null;
    private HttpSession session = null;

    @Inject
    public UserSessionImpl(Provider<HttpServletRequest> requestProvider) {
        provider = requestProvider;
    }

    private UserSessionImpl(HttpServletRequest request) {
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

    public UserInfo getUserInfo() {
        HttpSession session = getSession();

        String cip = "";
        String firstName = "";
        String lastName = "";
        String email = "";

        try {
            Assertion assertion = (Assertion) session.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
            cip = assertion.getPrincipal().getName();
            firstName = (String) assertion.getPrincipal().getAttributes().get("prenom");
            lastName = (String) assertion.getPrincipal().getAttributes().get("nomFamille");
            email = (String) assertion.getPrincipal().getAttributes().get("courriel");
        } catch (NullPointerException ex) {
            Logger.getLogger(UserSessionImpl.class.getName()).log(Level.SEVERE, "Session or CAS assertion is null, verify your web.xml or you try to access resources before authentication");
        }
        return new UserInfo(cip, firstName, lastName, email);
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