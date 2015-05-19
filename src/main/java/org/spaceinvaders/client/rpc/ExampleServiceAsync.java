package org.spaceinvaders.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

import org.spaceinvaders.shared.model.ExampleRPC;
import org.spaceinvaders.shared.model.TableDataTest;

import java.util.List;

public interface ExampleServiceAsync {
    void sayHello(AsyncCallback<ExampleRPC> async);

    void fetchData(AsyncCallback<List<TableDataTest>> async);
}
