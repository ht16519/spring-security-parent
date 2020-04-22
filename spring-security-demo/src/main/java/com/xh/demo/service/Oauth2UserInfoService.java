package com.xh.demo.service;

public interface Oauth2UserInfoService {

    boolean existBySourceAndUserId(String userId, String source);

    void binding(String userId, String providerId, String source);
}
