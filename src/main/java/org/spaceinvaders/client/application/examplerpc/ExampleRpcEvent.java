//package org.spaceinvaders.client.application.examplerpc;
//
//import com.google.gwt.event.shared.GwtEvent;
//import com.google.gwt.event.shared.HasHandlers;
//
//import org.spaceinvaders.shared.model.ExampleRPC;
//
//import java.util.List;
//
///**
// * Created with IntelliJ IDEA Project: projetS6 on 5/18/2015
// *
// * @author antoine
// */
//public class ExampleRpcEvent extends GwtEvent<ExampleRpcEventHandler> {
//    public static Type<ExampleRpcEventHandler> TYPE = new Type<ExampleRpcEventHandler>();
//
//    public Type<ExampleRpcEventHandler> getAssociatedType() {
//        return TYPE;
//    }
//
//    ExampleRPC modelRPC;
//
//    public ExampleRpcEvent() {
//    }
//
//    public ExampleRpcEvent(ExampleRPC modelRPC) {
//        this.modelRPC = modelRPC;
//    }
//
//    protected void dispatch(ExampleRpcEventHandler handler) {
//        handler.onExampleRpc(this);
//    }
//
//    //TODO what is that ?!?
//    public static void fire(HasHandlers source, ExampleRPC data) {
//        source.fireEvent(new ExampleRpcEvent(data));
//    }
//}
