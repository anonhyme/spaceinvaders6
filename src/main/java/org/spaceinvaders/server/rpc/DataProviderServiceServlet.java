package org.spaceinvaders.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import org.spaceinvaders.client.rpc.DataProviderService;
import org.spaceinvaders.shared.model.DataProviderModel;
import org.spaceinvaders.shared.model.TableDataTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 5/16/2015
 *
 * @author antoine
 */
public class DataProviderServiceServlet extends RemoteServiceServlet implements DataProviderService {
    @Override
    public DataProviderModel sayHello() {
        DataProviderModel hello = new DataProviderModel("hello");
        return hello;
    }

    @Override
    public List<TableDataTest> fetchData() {
        List<TableDataTest> testList = new ArrayList<TableDataTest>();
        for (int i = 0; i < 10; i++) {
            testList.add(new TableDataTest("Test " + i, "Test " + i, "Test " + i));
//            dataProvider.getList().add(new TableDataTest("Test " + i, "Test " + i, "Test " + i));
        }
        return testList;
    }
}
