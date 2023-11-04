package com.dlut.efmsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dlut.efmsystem.pojo.Menu;
import com.dlut.efmsystem.pojo.Role;
import com.dlut.efmsystem.pojo.RoleVO;

import java.util.List;

public interface RoleManageService {
    Integer create(Role r);
    Boolean remove(Integer id);
    Boolean modify(Role r);
    IPage<Role> getPage(int current,int num,Role role);
    RoleVO getRoleDetail(int id);
    Role getOneRole(int id);
    List<Role> listAllRole();
    Boolean checkRole(Role role);
    Boolean match(Integer id, List<Integer> menus);
    Boolean removeMatch(Integer roleid);
    List<Menu> listMenus(int roleid);
    String getNameById(Integer id);
}
