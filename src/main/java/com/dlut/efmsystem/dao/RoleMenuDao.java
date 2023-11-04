package com.dlut.efmsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dlut.efmsystem.pojo.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface RoleMenuDao extends BaseMapper<RoleMenu> {

    @Select("SELECT menu_id FROM role_menu WHERE role_id = #{id}")
    List<Integer> selectMenuId(@Param("id") int id);

}
