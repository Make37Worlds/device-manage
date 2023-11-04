package com.dlut.efmsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dlut.efmsystem.controller.util.Result;
import com.dlut.efmsystem.pojo.Role;
import com.dlut.efmsystem.pojo.RoleVO;
import com.dlut.efmsystem.service.RoleManageService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/rolemanage")
public class RoleManageController {

    @Resource
    private RoleManageService roleManageService;

    @GetMapping("{currentPage}/{size}")
    public Result getPage(@PathVariable int currentPage,@PathVariable int size,Role role) {
        IPage<Role> page = roleManageService.getPage(currentPage, size, role);
        if (currentPage > page.getPages()) {
            page = roleManageService.getPage((int) page.getPages(), size, role);
        }
        return new Result(true, page);
    }

    @GetMapping("{id}")
    public Result getRoleDeatil(@PathVariable int id){
        RoleVO roleDetail = roleManageService.getRoleDetail(id);
        if(roleDetail  != null){
            return new Result(true,roleDetail);
        }else{
            return new Result(false,"无法获取到角色信息，请稍后再试");
        }
    }

    @GetMapping("/getname/{id}")
    public Result getName(@PathVariable int id){
        String name = roleManageService.getNameById(id);
        if(Strings.isNotEmpty(name)){
            return new Result(true,name,"角色名称获取成功");
        }else{
            return new Result(false,"无法获取到角色名称，请稍后再试");
        }
    }

    @PostMapping
    public Result addRole(@RequestBody RoleVO roleVO) throws RuntimeException{
        Role newRole = roleVO.getRole();
        if(roleManageService.checkRole(newRole)){
            Integer id= roleManageService.create(newRole);
            List<Integer> menuList=roleVO.getMenus();
            roleManageService.match(id,menuList);
            return new Result(true,id,"角色新增成功");
        }
        return new Result(false,"名称或权限字符重复，请修改后再试");
    }

    @PutMapping
    public Result updateRole(@RequestBody RoleVO roleVO) throws RuntimeException{
        Role newRole = roleVO.getRole();
        if(roleManageService.checkRole(newRole)){
            Boolean flag = roleManageService.modify(newRole);
            if(flag){
                roleManageService.removeMatch(roleVO.getId());//删除原来的菜单与角色的联系
                roleManageService.match(roleVO.getId(),roleVO.getMenus());//建立新联系
            }
            return new Result(flag,flag?"角色修改成功":"角色修改失败，请稍后重试");
        }
        return new Result(false,"名称或权限字符重复，请修改后再试");
    }

    @DeleteMapping("{id}")
    public Result deleteRole(@PathVariable Integer id){
        if(roleManageService.remove(id)){
            //roleManageService.removeMatch(id);
            return new Result(true,"角色删除成功");
        }
        return new Result(false,"角色删除失败，请稍后再试");
    }
}
