package org.spaceinvaders.client.application.ui.menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.UiHandlers;
import com.gwtplatform.mvp.client.View;
import org.gwtbootstrap3.client.ui.*;
import org.gwtbootstrap3.client.ui.html.Span;
import org.spaceinvaders.client.application.home.HomePagePresenter;
import org.spaceinvaders.client.place.NameTokens;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainMenuView extends ViewWithUiHandlers   implements MainMenuPresenter.MyView {


    interface Binder extends UiBinder<Widget, MainMenuView> {
    }

    private static Binder uiBinder = GWT.create(Binder.class);

    @UiField
    DropDownMenu dropDownMenu;

    @UiField
    Span spanUsername;

    @UiField
    NavbarLink navbarLinkDisconnect;

    @Inject
    MainMenuView() {
        initWidget(uiBinder.createAndBindUi(this));
        spanUsername.setText("Louis-Olivier St-Laurent");
        this.addNavbarLinkInDropDown("Session 1", NameTokens.home);
        this.addNavbarLinkInDropDown("Session 2", NameTokens.home);
        this.addNavbarLinkInDropDown("Session 3", NameTokens.home);
    }

    @Override
    protected void onAttach() {
        super.onAttach();
    }

    private void addNavbarLinkInDropDown(String name, String nameToken) {
        AnchorListItem anchorListItem = new AnchorListItem(name);
        anchorListItem.setTargetHistoryToken(nameToken);
        dropDownMenu.add(anchorListItem);
    }

    @UiHandler("navbarLinkDisconnect")
    void onClick(ClickEvent event) {
        if (getUiHandlers() != null) {
            //getUiHandlers().disconnect();
        }
    }


}
