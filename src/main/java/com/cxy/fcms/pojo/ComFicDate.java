package com.cxy.fcms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cxy.fcms.util.DateUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
public class ComFicDate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 小说id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    private String ficData;

    private String createTime;

    private String updateTime;

    public void setGmtCreate(Object createTime) {
        if (createTime instanceof Date) {
            this.createTime = DateUtil.dateToString((Date) createTime);
        } else {
            this.createTime = String.valueOf(createTime);
        }
    }

    public void setGmtModified(Object updateTime) {
        if (updateTime instanceof Date) {
            this.createTime = DateUtil.dateToString((Date) updateTime);
        } else {
            this.createTime = String.valueOf(updateTime);
        }
    }

}
