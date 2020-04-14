package com.xh.demo.dao.mapper;

import com.xh.demo.dao.base.IBaseMapper;
import com.xh.demo.domain.po.User;
import com.xh.demo.domain.vo.UserDetails4OAuth2Vo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends IBaseMapper<User> {
    /**
     * @Name batchDelete
     * @Description 批量删除
     */
    int batchDelete(List<Integer> ids);

    UserDetails4OAuth2Vo selectByProviderId(@Param("providerId") String providerId);
}