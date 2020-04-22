package com.xh.demo.dao.mapper;

import com.xh.demo.dao.base.IBaseMapper;
import com.xh.demo.domain.auth.UserDetailsVo;
import com.xh.demo.domain.po.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends IBaseMapper<User> {
    /**
     * @Name batchDelete
     * @Description 批量删除
     */
    int batchDelete(List<Integer> ids);

    UserDetailsVo selectByProviderId(@Param("providerId") String providerId, @Param("source") String source);
}