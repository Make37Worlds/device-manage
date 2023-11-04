package com.dlut.efmsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dlut.efmsystem.controller.util.Result;
import com.dlut.efmsystem.pojo.*;
import com.dlut.efmsystem.service.AddDeclarationService;
import com.dlut.efmsystem.service.FixLogService;
import com.dlut.efmsystem.service.impl.AddDeclarationServiceImpl;
import com.dlut.efmsystem.service.impl.FixLogServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/approve")
public class ApproveController {

    @Resource
    AddDeclarationService addDeclarationService;

    @Resource
    FixLogService fixLogService;

    @GetMapping("{currentPage}/{size}/{id}")
    public Result getUnapproved(@PathVariable int currentPage, @PathVariable int size,@PathVariable String id){
        IPage<AddDeclaration> page = addDeclarationService.getUnapproved(currentPage,size,id);
        if(currentPage>page.getPages()){
            page = addDeclarationService.getUnapproved((int)page.getPages(),size,id);
        }
        return new Result(true,page,"待审核追加申报单获取成功");
    }

    @GetMapping("{id}")
    public Result getAddDeclarationVO(@PathVariable int id){
        AddDeclarationVO addDeclarationVO=new AddDeclarationVO();
        AddDeclaration addDeclaration= addDeclarationService.getOneAddDeclaration(id);
        if (addDeclaration != null) {
            FixLog fixLog = fixLogService.getOneLog(addDeclaration.getLogId());
            if (fixLog != null) {
                addDeclarationVO.setAddDeclaration(addDeclaration);
                addDeclarationVO.setFixLog(fixLog);
                return new Result(true,addDeclarationVO,"信息获取成功");
            }
            else {
                throw new RuntimeException("维修单信息获取失败，请稍后再试");
            }
        }
        else{
            throw new RuntimeException("追加申报单信息获取失败，请稍后再试");
        }
    }

    @PutMapping
    public Result approve(@RequestBody AddDeclaration addDeclaration){
        if(addDeclarationService.setApprovalInfo(addDeclaration)){
            return new Result(true,"审批成功");
        }else{
            return new Result(false,"审批失败，请稍后再试");
        }
    }

}
