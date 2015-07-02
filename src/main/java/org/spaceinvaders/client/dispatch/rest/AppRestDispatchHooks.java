package org.spaceinvaders.client.dispatch.rest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Response;

import com.gwtplatform.dispatch.rest.client.RestDispatchHooks;
import com.gwtplatform.dispatch.rest.shared.RestAction;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AppRestDispatchHooks implements RestDispatchHooks {
    public static boolean HookEnabled = true;
    private static final Logger logger = Logger.getLogger(AppRestDispatchHooks.class.getName());

    @Override
    public void onExecute(RestAction action) {
        if (HookEnabled) {
            GWT.log("onExecute " + "Executing rest dispatch " + action.getPath() + " resource action");
        }
    }

    @Override
    public void onSuccess(RestAction action, Response response, Object result) {
        if (HookEnabled) {
            logger.log(Level.SEVERE, "Successfully execute " + action.getPath() + ", result: " + response.getText());
            GWT.log("onSuccess " + "Successfully execute " + action.getPath() + ", result: " + response.getText());
        }
    }

    @Override
    public void onFailure(RestAction action, Response response, Throwable caught) {
        if (HookEnabled) {
            logger.log(Level.SEVERE, "Failed to execute " + action.getPath() + ", result: "
                    + response.getStatusText() + " " + response.getText() + " " + caught.getMessage());

            GWT.log("onFailure " + "Failed to execute " + action.getPath() + ", result: "
                    + response.getStatusText() + " " + response.getText() + " " + caught.getMessage());
        }
    }
}
