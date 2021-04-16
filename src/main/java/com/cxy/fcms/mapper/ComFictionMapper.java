package com.cxy.fcms.mapper;

import com.cxy.fcms.pojo.ComFiction;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈新予
 * @since 2021-04-15
 */
@Component
@SuppressWarnings("all")
public interface ComFictionMapper extends BaseMapper<ComFiction> {

    int addFiction(HashMap<String,String> map);

    int addFictionData(@Param("id") String id,@Param("ficId") String ficId, @Param("text") String text);

    int addFictionType(@Param("id") String id,@Param("ficId") String ficId,@Param("typeId") String typeID);


}
