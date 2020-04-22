package com.xh.demo.service.impl;

import com.xh.demo.dao.mapper.Oauth2UserInfoMapper;
import com.xh.demo.domain.po.Oauth2UserInfo;
import com.xh.demo.service.Oauth2UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class Oauth2UserInfoServiceImpl implements Oauth2UserInfoService {

    @Autowired
    private Oauth2UserInfoMapper oauth2UserInfoMapper;

    @Override
    public boolean existBySourceAndUserId(String userId, String source) {
        return oauth2UserInfoMapper.selectCount(Oauth2UserInfo.builder()
                .userId(Long.valueOf(userId))
                .source(source).build()) > 0;
    }

    @Override
    public void binding(String userId, String providerId, String source) {
        oauth2UserInfoMapper.insertSelective(Oauth2UserInfo.builder()
                .userId(Long.valueOf(userId))
                .providerId(providerId)
                .source(source)
                .createTime(new Date())
                .state(10)
                .build()
        );
    }
}
