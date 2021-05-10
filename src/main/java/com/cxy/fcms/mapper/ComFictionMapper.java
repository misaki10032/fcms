package com.cxy.fcms.mapper;

import com.cxy.fcms.pojo.ComFicDate;
import com.cxy.fcms.pojo.ComFiction;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

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

    int addFiction(HashMap<String, String> map);

    int addFictionData(@Param("id") String id, @Param("ficId") String ficId, @Param("text") String text);

    int addFictionType(@Param("id") String id, @Param("ficId") String ficId, @Param("typeId") String typeID);

    List<ComFiction> getFictions();

    List<ComFiction> getFictionsOrderByTime();

    List<ComFiction> getFictionsOrderByHost();

    int delFiction(String id);

    int delFicType(String id);

    int delFicData(String id);

    List<String> getFictionByTypeId(String tid);

    ComFicDate getFictionDataById(String id);

    List<ComFiction> getFictionslimit(@Param("pagelimit") int pagelimit, @Param("limit") int limit);

    List<ComFiction> searchFiction(String msg);

    ComFiction getFictionByName(String name);
}
