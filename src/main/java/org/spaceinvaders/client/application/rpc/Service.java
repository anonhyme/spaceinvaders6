package org.spaceinvaders.client.application.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.spaceinvaders.server.rpc.ServiceServlet;

/**
 * Created by AlexandraMaude on 2015-05-21.
 */
@RemoteServiceRelativePath("Cip")
public interface Service extends RemoteService {
    public String getCip();

}
