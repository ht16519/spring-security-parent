package com.xh.demo.service.impl;

import lombok.extern.slf4j.Slf4j;
import com.xh.demo.domain.po.User;
import com.xh.demo.commons.exception.BusinessException;
import com.xh.demo.commons.enums.MessageEnum;
import com.xh.demo.dao.mapper.UserMapper;
import com.xh.demo.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getById(Integer id){
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User findOne(User user){
        return userMapper.selectOne(user);
    }

    @Override
    public int add(User user){
        return userMapper.insertSelective(user);
    }

    @Override
    public int update(User user){
        if(user.getId() == null){
            throw BusinessException.build(MessageEnum.PARAMETER_VERIFICATION_ERROR_ID);
        }
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int deleteById(Integer id){
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public User getByUserName(String username) {
        return userMapper.selectOne(User.builder().username(username).build());
    }


}
