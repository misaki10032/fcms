package com.cxy.fcms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.cxy.fcms.util.DateUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 陈新予
 * @since 2021-04-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ComUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户手机号
     */
    private String userPhone;

    /**
     * 用户密码
     */
    private String userPwd;

    /**
     * 用户等级
     */
    private Integer userLevel;

    /**
     * 用户注册时间
     */
    private String gmtCreate;

    /**
     * 修改时间
     */
    private String gmtModified;

    /**
     * 逻辑删除
     */
    private Integer isDeleted;

    /**
     * 是否封禁
     */
    private Integer isBanned;

    public void setGmtCreate(Object gmtCreate) {
        if (gmtCreate instanceof Date) {
            this.gmtCreate = DateUtil.dateToString((Date) gmtCreate);
        } else {
            this.gmtCreate = String.valueOf(gmtCreate);
        }
    }

    public void setGmtModified(Object gmtModified) {
        if (gmtModified instanceof Date) {
            this.gmtModified = DateUtil.dateToString((Date) gmtModified);
        } else {
            this.gmtModified = String.valueOf(gmtModified);
        }
    }
}
