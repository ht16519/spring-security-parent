package com.xh.demo.dao.mapper;

import com.xh.demo.dao.base.IBaseMapper;
import com.xh.demo.domain.po.Oauth2UserInfo;
import java.util.List;

public interface Oauth2UserInfoMapper extends IBaseMapper<Oauth2UserInfo> {
    /**
     * @Name batchDelete
     * @Description 批量删除
     */
    int batchDelete(List<Long> ids);
}