package org.spaceinvaders.shared.dispatch;

/**
 * Created by AlexandraMaude on 2015-05-26.
 */

import com.gwtplatform.dispatch.rpc.shared.Result;

/**
 *  @deprecated replaced by {@link org.spaceinvaders.shared.api.UserInfoResource}
 */
@Deprecated public class GetUserInfoResult implements Result {
    private UserInfo userInfo;

    public GetUserInfoResult(final UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * For serialization only.
     */
    @SuppressWarnings("unused")
    private GetUserInfoResult() {
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}