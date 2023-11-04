package com.dlut.efmsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dlut.efmsystem.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface MenuDao extends BaseMapper<Menu> {

    @Select("SELECT * FROM menu WHERE id IN (SELECT menu_id FROM role_menu WHERE role_id = #{id} ) ")
    List<Menu> selectMenus(@Param("id") int id);
}
