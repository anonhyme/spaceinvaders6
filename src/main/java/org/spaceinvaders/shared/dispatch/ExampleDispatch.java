package org.spaceinvaders.shared.dispatch;

import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

import org.spaceinvaders.shared.result.ExampleResult;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 5/16/2015
 *
 * @author antoine
 */
public class ExampleDispatch extends UnsecuredActionImpl<ExampleResult> {
    String hello;

    public ExampleDispatch() {
    }

    public ExampleDispatch(String hello) {
        this.hello = hello;
    }

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }
}
