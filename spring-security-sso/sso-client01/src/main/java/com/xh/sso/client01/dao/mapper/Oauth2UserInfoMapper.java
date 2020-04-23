package com.xh.sso.client01.dao.mapper;

import com.xh.sso.client01.dao.base.IBaseMapper;
import com.xh.sso.client01.domain.po.Oauth2UserInfo;

import java.util.List;

public interface Oauth2UserInfoMapper extends IBaseMapper<Oauth2UserInfo> {
    /**
     * @Name batchDelete
     * @Description 批量删除
     */
    int batchDelete(List<Long> ids);
}