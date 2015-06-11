package org.spaceinvaders.shared.dispatch;

import java.io.Serializable;

/**
 * Created by hugbed on 2015-05-27.
 */
public class UserInfo implements Serializable {
    private String cip;

    public UserInfo(String cip) {
        this.cip = cip;
    }

    /**
     * For serialization only
     */
    @SuppressWarnings("unused")
    public UserInfo() {

    }

    public String getCip() {
        return cip;
    }
}
