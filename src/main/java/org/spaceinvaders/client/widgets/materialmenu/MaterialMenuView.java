
package org.spaceinvaders.client.widgets.materialmenu;

import com.google.gwt.query.client.GQuery;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.inject.Inject;

import com.gwtplatform.mvp.client.ViewImpl;

import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.DropDownMenu;
import org.gwtbootstrap3.client.ui.Navbar;
import org.gwtbootstrap3.client.ui.NavbarLink;
import org.gwtbootstrap3.client.ui.html.Span;
import org.spaceinvaders.client.resources.AppResources;

public class MaterialMenuView extends ViewImpl implements MaterialMenuPresenter.MyView {

    public interface Binder extends UiBinder<HTMLPanel, MaterialMenuView> {
    }

    @UiField
    DropDownMenu dropDownMenu;

    @UiField
    Span spanUsername;

    @UiField
    NavbarLink navbarLinkDisconnect;

    @UiField
    Navbar materialNavBar;

    private final AppResources appResources;

    @Inject
    MaterialMenuView(Binder binder, AppResources appResources) {
        initWidget(binder.createAndBindUi(this));
        this.addNavbarLinkInDropDown("Session 1", "#");
        this.addNavbarLinkInDropDown("Session 2", "#");
        this.addNavbarLinkInDropDown("Session 3", "#");
        this.appResources = appResources;
        //spanUsername.setText(user.toString());
        //GWT.log("setUserId " + user);
    }

    @Override
    public void setUserName(String userName) {
        this.spanUsername.setText(userName);
        GQuery.console.log("Username ::: " + userName);
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        materialNavBar.addStyleName(appResources.topNavBar().material());
    }

    @Override
    public void setUiHandlers(MaterialMenuUiHandlers uiHandlers) {

    }

    private void addNavbarLinkInDropDown(String name, String nameToken) {
        AnchorListItem anchorListItem = new AnchorListItem(name);
        anchorListItem.setTargetHistoryToken(nameToken);
        dropDownMenu.add(anchorListItem);
    }

//    @UiHandler("navbarLinkDisconnect")
//    void onClick(ClickEvent event) {
//        if (getUiHandlers() != null) {
//            //getUiHandlers().disconnect();
//        }
//    }

}
