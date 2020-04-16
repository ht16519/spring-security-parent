package com.xh.demo.service.impl;

import lombok.extern.slf4j.Slf4j;
import com.xh.demo.domain.po.Oauth2UserInfo;
import com.xh.demo.commons.exception.BusinessException;
import com.xh.demo.commons.enums.MessageEnum;
import com.xh.demo.dao.mapper.Oauth2UserInfoMapper;
import com.xh.demo.service.Oauth2UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Service
public class Oauth2UserInfoServiceImpl implements Oauth2UserInfoService {

    @Autowired
    private Oauth2UserInfoMapper oauth2UserInfoMapper;

    @Override
    public Oauth2UserInfo getById(Long id){
        return oauth2UserInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public Oauth2UserInfo findOne(Oauth2UserInfo oauth2UserInfo){
        return oauth2UserInfoMapper.selectOne(oauth2UserInfo);
    }

    @Override
    public int add(Oauth2UserInfo oauth2UserInfo){
        return oauth2UserInfoMapper.insertSelective(oauth2UserInfo);
    }

    @Override
    public int update(Oauth2UserInfo oauth2UserInfo){
        if(oauth2UserInfo.getId() == null){
            throw BusinessException.build(MessageEnum.PARAMETER_VERIFICATION_ERROR_ID);
        }
        return oauth2UserInfoMapper.updateByPrimaryKeySelective(oauth2UserInfo);
    }

    @Override
    public int deleteById(Long id){
        return oauth2UserInfoMapper.deleteByPrimaryKey(id);
    }


}
