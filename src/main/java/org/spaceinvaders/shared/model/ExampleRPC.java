package org.spaceinvaders.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 5/16/2015
 *
 * @author antoine
 */
public class ExampleRPC implements IsSerializable {
    private String response;


    public ExampleRPC() {
    }

    public ExampleRPC(String input) {
        super();
        this.response = input;
    }

    public String getResponse() {
        return response;
    }
}
