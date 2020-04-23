package com.xh.sso.client01.service;


import com.xh.sso.client01.domain.auth.UserDetailsVo;
import com.xh.sso.client01.domain.po.User;

public interface UserService {

    /**
    * @name add
    * @description 新增操作
    * @return int
    */
    int add(User user);

    /**
    * @name getById
    * @description 通过id获取单条数据
    * @param id
    */
    User getById(Integer id);

    /**
    * @name findOne
    * @description 条件查询一条数据
    * @param user
    */
    User findOne(User user);

    /**
    * @name update
    * @description 修改数据
    * @return int
    */
    int update(User user);

    /**
    * @name removeById
    * @description 删除（物理删除）
    * @param id
    * @return int
    */
    int deleteById(Integer id);

    User getByUserName(String username);

    User getByMobile(String mobile);

}
