package org.spaceinvaders.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

import org.spaceinvaders.shared.model.DataProviderModel;
import org.spaceinvaders.shared.model.TableDataTest;

import java.util.List;

public interface DataProviderServiceAsync {
    void sayHello(AsyncCallback<DataProviderModel> async);

    void fetchData(AsyncCallback<List<TableDataTest>> async);
}
