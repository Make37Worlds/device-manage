package com.dlut.efmsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dlut.efmsystem.controller.util.Result;
import com.dlut.efmsystem.pojo.Employee;
import com.dlut.efmsystem.service.EmployeeService;
import com.dlut.efmsystem.service.RoleManageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/empmanage")
public class EmployeeManageController {

    @Resource
    EmployeeService employeeService;
    @Resource
    RoleManageService roleManageService;

    @GetMapping("{currentPage}/{size}")
    public Result getPage(@PathVariable int currentPage, @PathVariable int size,Employee employee){
        IPage<Employee> page = employeeService.getPage(currentPage,size,employee);
        if(currentPage>page.getPages()){
            page= employeeService.getPage((int)page.getPages(),size,employee);
        }
        return new Result(true,page,"人员信息获取成功");
    }

    @GetMapping("{id}")
    public Result getEmployee(@PathVariable String id){
        Employee employee = employeeService.getOneEmployee(id);
        return new Result(true,employee,"人员信息获取成功");
    }

    @GetMapping("/getname/{id}")
    public Result getName(@PathVariable String id){
        String name = employeeService.getNameById(id);
        return new Result(true,name,"人员姓名获取成功");
    }

    @PostMapping
    public Result addEmployee(@RequestBody Employee employee){
        //前端获取日期or后端获取当前日期
        String id = employeeService.create(employee);
        if(!id.equals("")){
            return new Result(true,id,"人员新增成功");
        }else{
            return new Result(false,"人员新增失败，请稍后再试");
        }
    }

    @DeleteMapping("{id}")
    public Result deleteEmployee(@PathVariable String id){
        if(employeeService.remove(id)){
            return new Result(true,"人员删除成功");
        }else{
            return new Result(false,"人员删除失败，请稍后再试");
        }
    }

    @PutMapping
    public Result updateEmployee(@RequestBody Employee employee){
        if(employeeService.modify(employee)){
            return new Result(true,"人员信息修改成功");
        }else{
            return new Result(false,"人员信息修改失败，请稍后再试");
        }
    }

    @GetMapping("/allrole")
    public Result getAllRole(){
        return new Result(true,roleManageService.listAllRole(),"全部角色已获取");
    }
}
