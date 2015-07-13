



package org.spaceinvaders.client.application.resulttable;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

import org.spaceinvaders.client.place.NameTokens;

public class ResultTablePresenter extends Presenter<ResultTablePresenter.MyView, ResultTablePresenter.MyProxy> implements ResultTableUiHandlers {
	interface MyView extends View, HasUiHandlers<ResultTableUiHandlers> {
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_ResultTable = new Type<RevealContentHandler<?>>();

	@NameToken(NameTokens.resultTable)
	@ProxyStandard
	public interface MyProxy extends ProxyPlace<ResultTablePresenter> {
	}

	@Inject
	public ResultTablePresenter(
			EventBus eventBus,
			MyView view,
			MyProxy proxy) {
		super(eventBus, view, proxy, RevealType.Root);

		getView().setUiHandlers(this);
	}

	protected void onBind() {
		super.onBind();
	}


	protected void onHide() {
		super.onHide();
	}

	protected void onUnbind() {
		super.onUnbind();
	}

	protected void onReset() {
		super.onReset();
	}


}
