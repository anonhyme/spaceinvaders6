/*
 * Copyright 2013 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.spaceinvaders.client.application.dispatch.rest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Response;

import com.gwtplatform.dispatch.rest.client.RestDispatchHooks;
import com.gwtplatform.dispatch.rest.shared.RestAction;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AppRestDispatchHooks implements RestDispatchHooks {
    private static final boolean HookEnabled = false;
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
