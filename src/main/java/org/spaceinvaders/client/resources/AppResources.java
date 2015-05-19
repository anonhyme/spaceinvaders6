/*
 * Copyright 2015 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.spaceinvaders.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface AppResources extends ClientBundle {

//    interface Normalize extends CssResource {
//    }
//
//    interface MyStyle extends CssResource {
//
//
//    }

    interface Style extends CssResource {
        @ClassName("pricing-table")
        String pricingTable();

        @ClassName("panel-heading-landing")
        String panelHeadingLanding();

        @ClassName("panel-heading-landing-box")
        String panelHeadingLandingBox();

        @ClassName("panel-title-landing")
        String panelTitleLanding();

        @ClassName("btn-price")
        String btnPrice();

        @ClassName("panel-body-landing")
        String panelBodyLanding();

        @ClassName("panel")
        String panel();

        @ClassName("panel-footer-landing")
        String panelFooterLanding();

        @ClassName("btn")
        String btn();

        @ClassName("panel-footer")
        String panelFooter();
    }

//    @Source("css/normalize.gss")
//    Normalize normalize();

    @Source("css/style.gss")
    Style style();

//    @Source("css/myStyle.css")
//    MyStyle myStyle();
}
