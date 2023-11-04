package com.dlut.efmsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dlut.efmsystem.controller.util.Result;
import com.dlut.efmsystem.pojo.Equipment;
import com.dlut.efmsystem.service.EquipmentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/eqmmanage")
public class EquipmentManageController {

    @Resource
    EquipmentService equipmentService;

    @GetMapping("{currentPage}/{size}")
    public Result getPage(@PathVariable int currentPage, @PathVariable int size,Equipment equipment){
        IPage<Equipment> page = equipmentService.getPage(currentPage,size,equipment);
        if(currentPage>page.getPages()){
            page= equipmentService.getPage((int)page.getPages(),size,equipment);
        }
        return new Result(true,page,"设施信息获取成功");
    }

    @GetMapping("{id}")
    public Result getEquipment(@PathVariable String id){
        Equipment equipment = equipmentService.getOneEquipment(id);
        return new Result(true,equipment,"设施信息获取成功");
    }

    @PostMapping
    public Result addEmployee(@RequestBody Equipment equipment){
        //前端获取日期or后端获取当前日期
        String id = equipmentService.create(equipment);
        if(!id.equals("")){
            return new Result(true,id,"设施新增成功");
        }else{
            return new Result(false,"设施新增失败，请稍后再试");
        }
    }

    @DeleteMapping("{id}")
    public Result deleteEmployee(@PathVariable String id){
        if(equipmentService.remove(id)){
            return new Result(true,"设施删除成功");
        }else{
            return new Result(false,"设施删除失败，请稍后再试");
        }
    }

    @PutMapping
    public Result updateEmployee(@RequestBody Equipment equipment){
        if(equipmentService.modify(equipment)){
            return new Result(true,"设施信息修改成功");
        }else{
            return new Result(false,"设施信息修改失败，请稍后再试");
        }
    }
}
