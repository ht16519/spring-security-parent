package com.xh.sso.client01.service;

public interface Oauth2UserInfoService {

    boolean existBySourceAndUserId(String userId, String source);

    void binding(String userId, String providerId, String source);
}
