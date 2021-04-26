package com.cxy.fcms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysAdminInfo implements Serializable {

    @TableId(value = "admin_id", type = IdType.ID_WORKER)
    private String adminId;


    /**
     * 姓名
     */
    private String adminName;

    /**
     * 年龄
     */
    private String adminAge;

    /**
     * 性别
     */
    private String adminSex;

    /**
     * 地址
     */
    private String adminAddress;

    /**
     * 用户星级
     */
    private String adminStar;

    /**
     * 个人宣言
     */
    private String adminDec;
    /**
     * 昵称
     */
    private String adminCall;
    /**
     * 邮箱
     */
    private String adminEmail;
    /**
     * 钱
     */
    private String adminMoney;
}
