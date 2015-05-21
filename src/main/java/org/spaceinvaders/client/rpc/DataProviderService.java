package org.spaceinvaders.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import org.spaceinvaders.shared.model.DataProviderModel;
import org.spaceinvaders.shared.model.TableDataTest;

import java.util.List;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 5/16/2015
 *
 * @author antoine
 */
@RemoteServiceRelativePath("hello")
public interface DataProviderService extends RemoteService {
    public DataProviderModel sayHello();

    public List<TableDataTest> fetchData();
}
