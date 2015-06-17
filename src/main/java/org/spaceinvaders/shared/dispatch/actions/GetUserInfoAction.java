package org.spaceinvaders.shared.dispatch;

/**
 * Created by AlexandraMaude on 2015-05-26.
 */

import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

/**
 *  @deprecated replaced by {@link org.spaceinvaders.shared.api.UserInfoResource}
 */
@Deprecated public class GetUserInfoAction extends UnsecuredActionImpl<GetUserInfoResult> {
    public GetUserInfoAction() {
    }
}