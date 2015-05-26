package org.spaceinvaders.shared.dispatch;

/**
 * Created by AlexandraMaude on 2015-05-26.
 */
import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.shared.Result;
import org.spaceinvaders.server.cas.UserSessionImpl;

/**
 * The result of a {@link SendToServerAction} action.
 */
public class SendToServerResult implements Result {
    private String response;
    private String cip;

    public SendToServerResult(final String response) {
        this.response = response;
    }

    /**
     * For serialization only.
     */
    @SuppressWarnings("unused")
    private SendToServerResult() {
    }

    public String getResponse() {
        return response;
    }
}