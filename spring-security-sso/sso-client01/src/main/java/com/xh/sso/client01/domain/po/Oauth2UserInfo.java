package com.xh.sso.client01.domain.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_oauth2_user_info")
public class Oauth2UserInfo implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @Id
    @Column(name = "user_id")
    private Long userId;

    /**
     * 提供商
     */
    private String source;

    /**
     * 提供商唯一凭证id
     */
    @Column(name = "provider_id")
    private String providerId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 状态
     */
    private Integer state;


}