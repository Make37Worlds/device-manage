package com.dlut.efmsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dlut.efmsystem.controller.util.Result;
import com.dlut.efmsystem.pojo.DispatchVO;
import com.dlut.efmsystem.pojo.Employee;
import com.dlut.efmsystem.pojo.FixLog;
import com.dlut.efmsystem.pojo.FixLogVO;
import com.dlut.efmsystem.service.EmployeeService;
import com.dlut.efmsystem.service.EquipmentService;
import com.dlut.efmsystem.service.FixLogService;
import com.dlut.efmsystem.service.impl.EmployeeServiceImpl;
import com.dlut.efmsystem.service.impl.FixLogServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/dispatch")
public class DispatchController {

    @Resource
    FixLogService fixLogService ;
    @Resource
    EmployeeService employeeService ;
    @Resource
    EquipmentService equipmentService;

    @GetMapping("{currentPage}/{size}/{id}")
    public Result getDispatchPage(@PathVariable int currentPage, @PathVariable int size,@PathVariable String id){
        IPage<FixLog> page = fixLogService.getDispatchPage(currentPage,size,id);
        if(currentPage>page.getPages()){
            page= fixLogService.getDispatchPage((int)page.getPages(),size,id);
        }
        return new Result(true,page,"待派遣维修单获取成功");
    }

    @GetMapping("{id}")
    public Result getDispatchInfo(@PathVariable Integer id){
        FixLog fixLog = fixLogService.getOneLog(id);
        if(fixLog==null){
            return new Result(false,"维修单信息获取失败，请稍后再试。");
        }
        int office=equipmentService.getOfficeById(fixLog.getFacId());
        FixLogVO fixLogVO = new FixLogVO(fixLog,office);
        return new Result(true,fixLogVO,"待派遣维修单获取成功");
    }

    @GetMapping
    public Result getFixer(){
        List<Employee> staffs = employeeService.listAllMaintenanceStaff();
        return new Result(true,staffs,"获取所有维修员成功") ;
    }

    @PutMapping
    public Result dispatch(@RequestBody DispatchVO dispatchVO){
        if(fixLogService.setDispatchInfo(dispatchVO.getLogId(), dispatchVO.getFixBy())){
            return new Result(true,"派遣成功");
        }else{
            return new Result(false,"派遣失败");
        }
    }

}
