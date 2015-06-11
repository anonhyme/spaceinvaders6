package org.spaceinvaders.shared.dispatch;

import java.io.Serializable;

/**
 * Created by hugbed on 2015-05-27.
 */
public class UserInfo implements Serializable {
    private String cip;
    private String firstName;
    private String lastName;
    private String email;

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

    public void setCip(String cip) {
        this.cip = cip;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
