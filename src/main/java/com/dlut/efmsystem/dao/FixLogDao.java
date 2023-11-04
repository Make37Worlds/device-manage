package com.dlut.efmsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dlut.efmsystem.pojo.FixLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Collection;
import java.util.List;

@Mapper
public interface FixLogDao extends BaseMapper<FixLog> {

    @Select("SELECT id FROM fix_log WHERE head = #{head}")
    List<Integer> getIdByHead(@Param("head") String head);
}
