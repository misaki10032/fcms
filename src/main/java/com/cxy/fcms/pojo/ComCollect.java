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
public class ComCollect implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 收藏id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    /**
     * 收藏的小说编号
     */
    private String ficId;

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 创建时间
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

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = DateUtil.dateToString(gmtCreate);
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = DateUtil.dateToString(gmtModified);
    }
}
