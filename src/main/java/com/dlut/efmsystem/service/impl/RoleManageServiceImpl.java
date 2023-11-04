package com.dlut.efmsystem.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlut.efmsystem.dao.MenuDao;
import com.dlut.efmsystem.dao.RoleDao;
import com.dlut.efmsystem.dao.RoleMenuDao;
import com.dlut.efmsystem.pojo.Menu;
import com.dlut.efmsystem.pojo.Role;
import com.dlut.efmsystem.pojo.RoleMenu;
import com.dlut.efmsystem.pojo.RoleVO;
import com.dlut.efmsystem.service.EmployeeService;
import com.dlut.efmsystem.service.RoleManageService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class RoleManageServiceImpl implements RoleManageService {

    @Resource
    private RoleDao roledao;

    @Resource
    private RoleMenuDao rolemenudao;

    @Resource
    private MenuDao menudao;

    @Resource
    private EmployeeService employeeService;

    @Override
    public Integer create(Role r) throws RuntimeException {
        if(roledao.insert(r)>0){
            return r.getId();
        }
        else{
            throw new RuntimeException("角色新增失败，请稍后再试");
        }
    }

    @Override
    public Boolean remove(Integer id) throws RuntimeException{
        if(employeeService.getCountByRole(id)>0){
            throw new RuntimeException("当前角色存在用户使用，删除失败");
        }
        return roledao.deleteById(id)>0;
    }

    @Override
    public Boolean modify(Role r) {
        return roledao.updateById(r)>0;
    }

    @Override
    public IPage<Role> getPage(int current, int num,Role r) {
        LambdaQueryWrapper<Role> lqw = new LambdaQueryWrapper<Role>();
        lqw.like(Strings.isNotEmpty(r.getName()),Role::getName,r.getName());
        lqw.like(Strings.isNotEmpty(r.getAuthority()),Role::getAuthority,r.getAuthority());
        lqw.like(Objects.nonNull(r.getState()),Role::getState,r.getState());
        IPage<Role> page = new Page(current,num);
        return roledao.selectPage(page,lqw);
    }

    @Override
    public RoleVO getRoleDetail(int id) {
        Role r = roledao.selectById(id);
        List<Integer> menus = rolemenudao.selectMenuId(id);
        return new RoleVO(r,menus);
    }

    @Override
    public Role getOneRole(int id) {
        return roledao.selectById(id);
    }

    @Override
    public List<Role> listAllRole(){
        return roledao.selectList(null);
    }

    private Boolean checkName(Role role) throws RuntimeException{
        LambdaQueryWrapper<Role> lqw = new LambdaQueryWrapper<Role>();
        lqw.eq(Strings.isNotEmpty(role.getName()),Role::getName,role.getName());
        Long count = roledao.selectCount(lqw);
        if(count==0){ //数据库中没有名字相同的角色
            return true;
        }else if(count == 1){//数据库中有名字相同的角色,返回该角色的id
            return Objects.equals(roledao.selectOne(lqw).getId(), role.getId());
        }else {
            throw new RuntimeException("数据库错误，角色名称已重复，请及时修正！");
        }
    }

    private Boolean checkAuthority(Role role) throws  RuntimeException{
        LambdaQueryWrapper<Role> lqw = new LambdaQueryWrapper<Role>();
        lqw.eq(Strings.isNotEmpty(role.getAuthority()),Role::getAuthority,role.getAuthority());
        Long count = roledao.selectCount(lqw);
        if(count==0){ //数据库中没有名字相同的权限字符
            return true;
        }else if(count == 1){//数据库中有权限字符相同的角色,返回该角色的id
            return Objects.equals(roledao.selectOne(lqw).getId(), role.getId());
        }else{
            throw  new RuntimeException("数据库错误，角色权限字符已重复，请及时修正！");
        }
    }

    //检查角色信息是否唯一
    @Override
    public Boolean checkRole(Role role) {
        return checkName(role)&&checkAuthority(role);
    }

    //将角色与菜单项进行连接
    @Override
    public Boolean match(Integer id, List<Integer>menus) throws RuntimeException {
        int flag = 0;
        for(int i=0;i<menus.size();i++){
            if(menudao.selectById(menus.get(i))!=null){ //确认该菜单项存在
                RoleMenu rm =new RoleMenu(id,menus.get(i));
                flag=rolemenudao.insert(rm);
            }else{
                throw new RuntimeException("菜单项不存在");
            }
        }
        return flag>0;
    }

    //删除角色时，删除与对应菜单项的所有连接
    @Override
    public Boolean removeMatch(Integer roleid) {
        LambdaQueryWrapper<RoleMenu> lqw = new LambdaQueryWrapper<RoleMenu>();
        lqw.eq(RoleMenu::getRoleId,roleid);
        return rolemenudao.delete(lqw)>0;
    }

    //根据角色id获取菜单项，用于登录
    @Override
    public List<Menu> listMenus(int roleid) {
        return menudao.selectMenus(roleid);
    }

    @Override
    public String getNameById(Integer id) {
        return roledao.selectById(id).getName();
    }
}
