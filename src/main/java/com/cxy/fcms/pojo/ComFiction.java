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
public class ComFiction implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 小说id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    /**
     * 小说名
     */
    private String ficName;

    /**
     * 作者
     */
    private String ficAuthor;

    /**
     * 封面
     */
    private String ficImg;

    /**
     * 类型
     */
    private String ficType;

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

    /**
     * 热度
     */
    private Integer ficHost;

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
