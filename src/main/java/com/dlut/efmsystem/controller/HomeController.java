package com.dlut.efmsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dlut.efmsystem.controller.util.Result;
import com.dlut.efmsystem.pojo.AddDeclaration;
import com.dlut.efmsystem.pojo.FixLog;
import com.dlut.efmsystem.pojo.FixLogVO;
import com.dlut.efmsystem.pojo.ResultReport;
import com.dlut.efmsystem.service.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Resource
    private FixLogService fixLogService;
    @Resource
    private ResultManageService resultReportService;
    @Resource
    private AddDeclarationService addDeclarationService;
    @Resource
    private EquipmentService equipmentService;

    @GetMapping("{currentPage}/{size}")
    public Result getPage(@PathVariable int currentPage, @PathVariable int size,FixLog fixLog){
        IPage<FixLog> page = fixLogService.getPage(currentPage,size,fixLog);
        if(currentPage>page.getPages()){
            page= fixLogService.getPage((int)page.getPages(),size,fixLog);
        }
        return new Result(true,page,"维修单获取成功");
    }

    @GetMapping("{id}")
    public Result getFixlogDetail(@PathVariable int id){
        FixLog fixLog = fixLogService.getOneLog(id);
        int office = equipmentService.getOfficeById(fixLog.getFacId());
        FixLogVO fixLogVO = new FixLogVO(fixLog,office);
        if(fixLog!=null){
            return new Result(true,fixLogVO,"获取成功");
        }else{
            return new Result(false,"获取失败");
        }
    }

    @PutMapping("/add")
    public Result reportAddDeclaration(@RequestBody AddDeclaration addDeclaration){
        int id =addDeclarationService.create(addDeclaration);
        if(id!=0){
            return new Result(true,id,"追加申报成功！");
        }
        else{
            return new Result(false,"追加申报失败，请检查该次追加申报的合理性！");
        }
    }

    @PutMapping("/res")
    public Result reportResult(@RequestBody ResultReport resultReport){
        int id = resultReportService.create(resultReport);
        return new Result(true,id,"成果上报成功");
    }

}
