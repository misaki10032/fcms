package com.cxy.fcms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cxy.fcms.util.DateUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

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
public class ComFicDate {

    private static final long serialVersionUID = 1L;

    /**
     * 小说id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    private String ficData;

    private String createTime;

    private String updateTime;

    public void setGmtCreate(Date createTime) {
        this.createTime = DateUtil.dateToString(createTime);
    }

    public void setGmtModified(Date updateTime) {
        this.updateTime = DateUtil.dateToString(updateTime);
    }

}
