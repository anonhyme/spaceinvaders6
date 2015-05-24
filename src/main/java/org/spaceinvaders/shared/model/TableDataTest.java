package org.spaceinvaders.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 5/18/2015
 *
 * @author antoine
 */
public class TableDataTest implements IsSerializable {
    private String field1;
    private String field2;
    private String field3;

    public TableDataTest() {
    }

    public TableDataTest(String field1, String field2, String field3) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public List<TableDataTest> getTestList() {
        List<TableDataTest> testList = new ArrayList<>();
        testList.add(new TableDataTest("TableDataTest1", "TableDataTest1", "TableDataTest1"));
        testList.add(new TableDataTest("TableDataTest2", "TableDataTest2", "TableDataTest2"));
        testList.add(new TableDataTest("TableDataTest3", "TableDataTest3", "TableDataTest3"));
        return testList;
    }

    public List<TableDataTest> getTestList2() {
        List<TableDataTest> testList = new ArrayList<>();
        testList.add(new TableDataTest("TableDataTestServer11", "TableDataTestServer11", "TableDataTestServer11"));
        testList.add(new TableDataTest("TableDataTestServer2", "TableDataTestServer2", "TableDataTestServer2"));
        testList.add(new TableDataTest("TableDataTestServer3", "TableDataTestServer3", "TableDataTestServer3"));
        return testList;
    }
}
