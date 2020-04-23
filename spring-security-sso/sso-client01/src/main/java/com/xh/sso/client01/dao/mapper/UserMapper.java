package com.xh.sso.client01.dao.mapper;


import com.xh.sso.client01.dao.base.IBaseMapper;
import com.xh.sso.client01.domain.po.User;

import java.util.List;

public interface UserMapper extends IBaseMapper<User> {
    /**
     * @Name batchDelete
     * @Description 批量删除
     */
    int batchDelete(List<Integer> ids);

}