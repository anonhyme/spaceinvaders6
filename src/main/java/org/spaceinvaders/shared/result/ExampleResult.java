package org.spaceinvaders.shared.result;

import com.gwtplatform.dispatch.rpc.shared.Result;
import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 5/16/2015
 *
 * @author antoine
 */
public class ExampleResult implements Result {
    private String hello;

    public ExampleResult() {
    }

    public ExampleResult(String hello) {
        this.hello = hello;
    }

    public String getHello() {
        return hello;
    }


}
