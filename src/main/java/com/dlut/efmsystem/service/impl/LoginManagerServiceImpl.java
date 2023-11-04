package com.dlut.efmsystem.service.impl;

import com.dlut.efmsystem.pojo.Employee;
import com.dlut.efmsystem.pojo.LoginDTO;
import com.dlut.efmsystem.pojo.Menu;
import com.dlut.efmsystem.pojo.Role;
import com.dlut.efmsystem.service.EmployeeService;
import com.dlut.efmsystem.service.LoginManagerService;
import com.dlut.efmsystem.service.RoleManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginManagerServiceImpl implements LoginManagerService{

    //存储当前所有登录用户的信息
    private static final Map<String, LoginDTO> userTokens = new ConcurrentHashMap<>();

    @Resource
    private EmployeeService employeeService;

    @Resource
    private RoleManageService roleManageService;


    @Override
    public LoginDTO login(String id, String password,String token) throws RuntimeException {

        String newToken = UUID.randomUUID().toString().replace("-", "");

        Employee employee = employeeService.getOneEmployee(id);
        if (!Optional.ofNullable(employee)
                .map(Employee::getPassword)
                .map(pw -> password.equals(pw))
                .orElse(false)) {
            throw new RuntimeException("账号或密码错误");
        }

        userTokens.remove(id);

        Long roleId = employee.getRoleId();
        Role role = roleManageService.getOneRole(roleId.intValue());
        if (Objects.isNull(role)) {
            throw new RuntimeException("用户无权限");
        }

        List<Menu> menus = roleManageService.listMenus(roleId.intValue());

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserId(id);
        loginDTO.setMenuList(menus);
        loginDTO.setToken(newToken);
        loginDTO.setRole(role.getAuthority());

        userTokens.put(id,loginDTO);

        return loginDTO;
    }

    @Override
    public Boolean setPassword(String id,String oldPassword, String password) throws IllegalStateException {
        if (!Objects.equals(oldPassword, employeeService.getPasswordById(id))) {
            throw new IllegalStateException("旧密码输入错误");
        }
        return employeeService.modify(new Employee().setId(id).setPassword(password));
    }

    @Override
    public Boolean isLogin(String token) {
        for (LoginDTO value : userTokens.values()) {
            if(value.getToken().equals(token)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void logout(String id) {
        userTokens.remove(id);
    }

}
