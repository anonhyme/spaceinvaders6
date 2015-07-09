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

    interface TopNavbar extends CssResource {

        @ClassName("materialTop")
        String materialTop();

        @ClassName("material")
        String material();

        @ClassName("material-container")
        String materialContainer();

        @ClassName("info")
        String info();
    }

    @Source("css/topNavbar.gss")
    TopNavbar topNavBar();
}
