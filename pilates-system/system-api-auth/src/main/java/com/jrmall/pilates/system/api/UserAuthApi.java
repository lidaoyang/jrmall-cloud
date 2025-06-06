package com.jrmall.pilates.system.api;

import com.jrmall.pilates.system.dto.UserAuthInfo;

public interface UserAuthApi {

    UserAuthInfo getUserAuthInfo(String username);
}
