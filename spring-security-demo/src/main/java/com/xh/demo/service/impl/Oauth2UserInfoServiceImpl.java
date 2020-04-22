package com.xh.demo.service.impl;

import com.xh.security.core.utils.UserDetailsUtil;
import com.xh.demo.dao.mapper.Oauth2UserInfoMapper;
import com.xh.demo.domain.po.Oauth2UserInfo;
import com.xh.demo.service.Oauth2UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Oauth2UserInfoServiceImpl implements Oauth2UserInfoService {

    @Autowired
    private Oauth2UserInfoMapper oauth2UserInfoMapper;

    @Override
    public boolean existBySourceAndUserId(String source) {
//        return oauth2UserInfoMapper.selectCount(Oauth2UserInfo.builder()
//                .userId(Long.valueOf(UserDetailsUtil.getUserDetailsVo().()))
//                .source(source).build()) == 0;
        return false;
    }
}
