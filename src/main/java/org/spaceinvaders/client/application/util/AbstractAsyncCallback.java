package org.spaceinvaders.client.application.util;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class AbstractAsyncCallback<T> implements AsyncCallback<T> {
    @Override
    public void onFailure(Throwable caught) {
    }
}