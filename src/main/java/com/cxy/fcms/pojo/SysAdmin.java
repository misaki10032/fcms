package com.cxy.fcms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
public class SysAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 管理员id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    /**
     * 管理员账号
     */
    private String adminNum;

    /**
     * 管理员密码
     */
    private String adminPwd;

    /**
     * 姓名
     */
    private String adminName;

    /**
     * 管理员权限
     */
    private String adminAuthority;

    /**
     * 管理员联系方式
     */
    private String adminPhone;


}
