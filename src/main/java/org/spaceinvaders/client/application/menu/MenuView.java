package org.spaceinvaders.client.application.menu;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import org.gwtbootstrap3.client.ui.*;
import org.gwtbootstrap3.client.ui.html.Span;
import org.spaceinvaders.client.resources.AppResources;
import org.spaceinvaders.shared.dto.SemesterInfo;

import java.util.List;

public class MenuView extends ViewWithUiHandlers<MenuUiHandlers> implements MenuPresenter.MyView {

    public interface Binder extends UiBinder<Widget, MenuView> {
    }

    @UiField
    DropDownMenu dropDownMenu;

    @UiField
    Span spanUsername;

    @UiField
    NavbarLink navbarLinkDisconnect;

    @UiField
    Navbar navBar;

    @UiField
    NavbarBrand navbarBrand;

    private final AppResources appResources;

    @Inject
    MenuView(Binder uiBinder, AppResources appResources) {
        initWidget(uiBinder.createAndBindUi(this));
//        this.addNavbarLinkInDropDown("Session 1", "#", 0);
//        this.addNavbarLinkInDropDown("Session 2", "#", 1);
//        this.addNavbarLinkInDropDown("Session 3", "#", 2);
        this.appResources = appResources;
    }

    @Override
    public void setUserName(String userName) {
        this.spanUsername.setText(userName);
        GQuery.console.log("Username ::: " + userName);
    }

    @Override
    public void setSemestersDropDownMenu(List<SemesterInfo> semestersInfo){
        for (SemesterInfo semesterInfo : semestersInfo) {
            addNavbarLinkInDropDown(semesterInfo.getLabel(),semesterInfo.getSemesterId());
        }
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        navBar.addStyleName(appResources.topNavBar().material());
    }

    private void addNavbarLinkInDropDown(String name, int semesterID /*,String nameToken*/ ) {
        AnchorListItem anchorListItem = new AnchorListItem(name);
        //anchorListItem.setTargetHistoryToken(nameToken);
        anchorListItem.addClickHandler(getClickHandler(semesterID));
        dropDownMenu.add(anchorListItem);
    }

    private ClickHandler getClickHandler(final int semesterID) {
        return new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().semesterChanged(semesterID);
            }
        };
    }

    @UiHandler("navbarLinkDisconnect")
    void onClick(ClickEvent event) {
        getUiHandlers().disconnect();
    }
}
