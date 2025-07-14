package com.jrmall.cloud.system.api;

import com.jrmall.cloud.system.dto.UserAuthInfo;

public interface UserAuthApi {

    UserAuthInfo getUserAuthInfo(String username);

    boolean logout();
}
