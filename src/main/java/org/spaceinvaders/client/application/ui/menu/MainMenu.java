package org.spaceinvaders.client.application.ui.menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.DropDownMenu;
import org.gwtbootstrap3.client.ui.NavbarNav;
import org.spaceinvaders.client.place.NameTokens;

import javax.inject.Inject;
import javax.lang.model.element.Name;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 5/18/2015
 *
 * @author antoine
 */

public class MainMenu extends Composite {

    interface MainMenuUiBinder extends UiBinder<Widget, MainMenu> {
    }

    private static MainMenuUiBinder uiBinder = GWT.create(MainMenuUiBinder.class);

    @UiField
    NavbarNav navBarNav;

    @UiField
    DropDownMenu dropDownMenu;

    public MainMenu() {
        initWidget(uiBinder.createAndBindUi(this));
        this.addNavbarLinkInDropDown("Session 1", NameTokens.home);
        this.addNavbarLinkInDropDown("Session 2", NameTokens.home);
        this.addNavbarLinkInDropDown("Session 3", NameTokens.home);
//        this.addNavbarLink("Main", NameTokens.home);
//        this.addNavbarLink("RPC Example", NameTokens.exampleRpc);
//        this.addNavbarLink("Bootstrap", NameTokens.bootstrapExamplePage);
//        this.addNavbarLink("Simple", NameTokens.simplePage);

    }

    @Override
    protected void onAttach() {
        super.onAttach();
    }


    private void addNavbarLink(String name, String nameToken) {
        AnchorListItem anchorListItem = new AnchorListItem(name);
        anchorListItem.setTargetHistoryToken(nameToken);
        navBarNav.add(anchorListItem);
    }

    private void addNavbarLinkInDropDown(String name, String nameToken) {
        AnchorListItem anchorListItem = new AnchorListItem(name);
        //anchorListItem.setTargetHistoryToken(nameToken); // Removed for now until we have NameTokens for each sessions
        dropDownMenu.add(anchorListItem);
    }
}
