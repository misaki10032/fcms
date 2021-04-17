package com.cxy.fcms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
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
 * 链接
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ComLink implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 链接id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    /**
     * 链接地址
     */
    private String linkData;

    /**
     * 链接名称
     */
    private String linkName;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 逻辑删除
     */
    private Integer isDeleted;


}
