package com.xh.sso.client01.service.impl;

import com.xh.sso.client01.commons.enums.MessageEnum;
import com.xh.sso.client01.commons.exception.BusinessException;
import com.xh.sso.client01.dao.mapper.Oauth2UserInfoMapper;
import com.xh.sso.client01.dao.mapper.UserMapper;
import com.xh.sso.client01.domain.po.User;
import com.xh.sso.client01.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private Oauth2UserInfoMapper oauth2UserInfoMapper;

    @Override
    public User getById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User findOne(User user) {
        return userMapper.selectOne(user);
    }

    @Override
    public int add(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public int update(User user) {
        if (user.getId() == null) {
            throw BusinessException.build(MessageEnum.PARAMETER_VERIFICATION_ERROR_ID);
        }
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int deleteById(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public User getByUserName(String username) {
        return userMapper.selectOne(User.builder().username(username).build());
    }

    @Override
    public User getByMobile(String mobile) {
        return userMapper.selectOne(User.builder().mobile(mobile).build());
    }



}
