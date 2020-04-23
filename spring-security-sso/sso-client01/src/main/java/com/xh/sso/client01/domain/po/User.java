package com.xh.sso.client01.domain.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_user")
public class User implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 用户表id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户最后一次登录时间
     */
    @Column(name = "last_login")
    private Date lastLogin;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态（-1：冻结，10：正常）
     */
    private Integer status;

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 性别（1，0：男：女）
     */
    private Integer sex;

    @Column(name = "open_id")
    private String openId;


}