package com.xh.demo.service.impl;

import com.xh.demo.commons.enums.MessageEnum;
import com.xh.demo.commons.exception.BusinessException;
import com.xh.demo.dao.mapper.Oauth2UserInfoMapper;
import com.xh.demo.dao.mapper.UserMapper;
import com.xh.demo.domain.po.Oauth2UserInfo;
import com.xh.demo.domain.po.User;
import com.xh.demo.domain.auth.SocialUserDetailsVo;
import com.xh.demo.service.UserService;
import com.xh.security.core.authentiation.oauth2.support.model.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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

    @Override
    public SocialUserDetailsVo getByProviderId(String providerId, String source) {
        return userMapper.selectByProviderId(providerId, source);
    }

    @Override
    @Transactional
    public SocialUserDetailsVo register(AuthUser authUser) {
        //1.静默注册新用户
        User user = User.builder()
                .avatar(authUser.getAvatar())
                .createTime(new Date())
                .email(authUser.getEmail())
                .nickname(authUser.getNickname())
                .sex(Integer.valueOf(authUser.getGender().getCode()))
                .status(10)
                .build();
        int res = userMapper.insertSelective(user);
        SocialUserDetailsVo userDetails4OAuth2Vo = null;
        if (res > 0) {
            //2.关联第三方信息表
            oauth2UserInfoMapper.insertSelective(Oauth2UserInfo.builder()
                    .createTime(new Date())
                    .source(authUser.getSource())
                    .userId(Long.valueOf(user.getId()))
                    .providerId(authUser.getUuid())
                    .build());
            //3.返回用户信息
            userDetails4OAuth2Vo = new SocialUserDetailsVo();
            BeanUtils.copyProperties(user, userDetails4OAuth2Vo);
        }
        return userDetails4OAuth2Vo;
    }


}
