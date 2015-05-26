package org.spaceinvaders.shared.dispatch;

/**
 * Created by AlexandraMaude on 2015-05-26.
 */
import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

public class SendToServerAction extends UnsecuredActionImpl<SendToServerResult> {
    private String textToServer;

    public SendToServerAction(String textToServer) {
        this.textToServer = textToServer;
    }

    /**
     * For serialization only.
     */
    @SuppressWarnings("unused")
    private SendToServerAction() {
    }

    public String getTextToServer() {
        return textToServer;
    }
}
