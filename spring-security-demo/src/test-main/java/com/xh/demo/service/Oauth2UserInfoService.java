package com.xh.demo.service;

import com.xh.demo.domain.po.Oauth2UserInfo;
import java.lang.*;

public interface Oauth2UserInfoService {

    /**
    * @name add
    * @description 新增操作
    * @return int
    */
    int add(Oauth2UserInfo oauth2UserInfo);

    /**
    * @name getById
    * @description 通过id获取单条数据
    * @param id
    */
    Oauth2UserInfo getById(Long id);

    /**
    * @name findOne
    * @description 条件查询一条数据
    * @param oauth2UserInfo
    */
    Oauth2UserInfo findOne(Oauth2UserInfo oauth2UserInfo);

    /**
    * @name update
    * @description 修改数据
    * @return int
    */
    int update(Oauth2UserInfo oauth2UserInfo);

    /**
    * @name removeById
    * @description 删除（物理删除）
    * @param id
    * @return int
    */
    int deleteById(Long id);

}
