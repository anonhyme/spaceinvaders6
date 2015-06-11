package org.spaceinvaders.client.application.ui.menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.DropDownMenu;
import org.gwtbootstrap3.client.ui.NavbarNav;
import org.spaceinvaders.client.place.NameTokens;

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
        this.addNavbarLinkInDropDown("Session 1", NameTokens.semesterGrades);
        this.addNavbarLinkInDropDown("Session 2", NameTokens.semesterGrades);
        this.addNavbarLinkInDropDown("Session 3", NameTokens.semesterGrades);
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
