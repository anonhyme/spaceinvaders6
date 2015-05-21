package org.spaceinvaders.client.application.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Created by AlexandraMaude on 2015-05-21.
 */
public interface ServiceAsync {
    void getCip(AsyncCallback<String> async);
}
