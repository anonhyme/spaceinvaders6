package org.spaceinvaders.server.dispatch;

import com.google.inject.Inject;
import com.google.inject.Provider;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.spaceinvaders.shared.dispatch.ExampleDispatch;
import org.spaceinvaders.shared.result.ExampleResult;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 5/16/2015
 *
 * @author antoine
 */
public class ExampleActionHandler implements ActionHandler<ExampleDispatch, ExampleResult> {
    private Provider<HttpServletRequest> requestProvider;
    private ServletContext servletContext;

    @Inject
    public ExampleActionHandler(Provider<HttpServletRequest> requestProvider, ServletContext servletContext) {
        this.requestProvider = requestProvider;
        this.servletContext = servletContext;
    }

    @Override
    public ExampleResult execute(ExampleDispatch action, ExecutionContext context) throws ActionException {
        return new ExampleResult(action.getHello());
    }

    @Override
    public Class<ExampleDispatch> getActionType() {
        return null;
    }

    @Override
    public void undo(ExampleDispatch action, ExampleResult result, ExecutionContext context) throws ActionException {

    }
}
